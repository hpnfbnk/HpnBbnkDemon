package com.hyphen.fbnk.bbnkdemon;

import com.hyphen.fbnk.bbnk.HpnBbnk;
import com.hyphen.fbnk.bbnk.ProcBbdata;
import com.hyphen.fbnk.bbnk.Util;
import com.hyphen.fbnk.bbnk.dto.DtoFileList;
import com.hyphen.fbnk.bbnk.msg.FnmTpKEduFine;
import com.hyphen.fbnk.bbnk.msg.FnmTpKsnet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class HpnBbnkDemon {
    static{System.setProperty("logback.configurationFile", "logback.xml");}
    private static final Logger log = LoggerFactory.getLogger(HpnBbnkDemon.class);
    private HpnBbnk hpnBbnk;

    public static void main(String[] args) {
        HpnBbnkDemon hpnBbnkDemon = new HpnBbnkDemon();
        hpnBbnkDemon.loopWork();
    }

    public void loopWork() {
        //감시업무 시작
        while(true){
            try{
                //환경파일 읽어들임.
                Config.setConfig();
                //HpnBbnk 생성
                if(Config.getCustomIpYn().equals("Y")){
                    hpnBbnk = new HpnBbnk(Config.getHpnSvrIpProd(), Config.getHpnSvrIpTest(), Config.getHpnSvrPort(), Config.getHpnSvrIpPrf(),
                            Config.getHpnSvrPortPrf(), Config.isZipYn(), Config.getNetBps(), Config.isDelnYn());
                }
                else{
                    hpnBbnk = new HpnBbnk(Config.isZipYn(), Config.getNetBps(), Config.isDelnYn());
                }
                //송신대상 감시
                chkSndData();
                //수신대상 감시
                chkRcvData();
            } catch (Exception e){
                log.error(e.toString());
                e.printStackTrace();
            } finally {
                //감시주기
                log.debug("WAIT...\n\n");
                try {Thread.sleep(Config.getMonitTerm()*60*1000L);} catch (InterruptedException ignored) {}
            }
        }//while end.
    }

    public void chkRcvData(){
        List<DtoFileList> rcvDataLists = null;

        for (String hpnId : Config.getHyphenId()) {
            log.debug("[chkRcvData] work 4 hyphenId:"+hpnId);

            //수신요청자의 통신비번 검색
            String hpnPwd = "";
            for(int i=0 ; i<Config.getHyphenId().length ; i++){
                if(Config.getHyphenId()[i].equals(hpnId))
                    try {hpnPwd = Config.getHyphenPwd()[i];}catch (Exception e){hpnPwd = "";}
            }

            if(rcvDataLists!=null)  rcvDataLists.clear();
            //hpnBbnk.recvDataMulti("KEDU", "9999", "ZZZ", "20220410", "20220414", "A", "KEDU", "./sample", "T");

            if(Config.getCocaDbYn().equals("Y"))
                rcvDataLists = hpnBbnk.recvDataMulti2DB(hpnId, Config.getRecvCd(), Config.getRecvDataTp(), Config.getFromDate(), Config.getToDate(),
                        Config.getReqTp(), Config.getFileNameTp(), Config.getRecvDir(), Config.getRunMode(), Config.getJdbcDriver(), Config.getJdbcUrl(),
                        Config.getJdbcUser(), Config.getJdbcPwd(), hpnPwd);
            else if(Config.getCocaDbYn().equals("DZN"))
                rcvDataLists = hpnBbnk.recvDataMulti2DB(hpnId, Config.getRecvCd(), Config.getRecvDataTp(), Config.getFromDate(), Config.getToDate(),
                        Config.getReqTp(), Config.getFileNameTp(), Config.getRecvDir(), Config.getRunMode(), Config.getJdbcDriver(), Config.getJdbcUrl(),
                        Config.getJdbcUser(), Config.getJdbcPwd(), hpnPwd, Config.getCocaDbYn(), Config.getCocaDbTblNm(), "", "");
            else
                rcvDataLists = hpnBbnk.recvDataMulti(hpnId, Config.getRecvCd(), Config.getRecvDataTp(), Config.getFromDate(), Config.getToDate(),
                    Config.getReqTp(), Config.getFileNameTp(), Config.getRecvDir(), Config.getRunMode(), hpnPwd);

            if(rcvDataLists.isEmpty())
                log.debug("[chkRcvData]("+hpnId+") NO_DATA");
            else
                for (DtoFileList rcvDataList : rcvDataLists) log.debug("[chkRcvData]("+hpnId+") : "+rcvDataList);
            //송수신내역 DB기록
            if(Config.getHistDbYn().equals("Y") && !rcvDataLists.isEmpty())
                new ProcBbdata().srHst2DB(rcvDataLists, "R", Config.getJdbcDriver(), Config.getJdbcUrl(), Config.getJdbcUser(), Config.getJdbcPwd());
        }
    }

    public void chkSndData(){
        List<DtoFileList> sndDataLists = new ArrayList<>();
        File fpath = new File(Config.getSendDir());
        File[] files = fpath.listFiles();
        for (File file : files)
            if(file.isFile())   setSndFile(file, sndDataLists);
        //for (DtoFileList dtoFileList : sndDataList) log.debug("[chkSndData] "+dtoFileList);
        List<DtoFileList> resultLists = hpnBbnk.sendDataMulti(Config.getHyphenId()[0], sndDataLists, Config.getFileNameTp(), Config.getRunMode());
        //결과처리
        resultTreat(resultLists);
        //송수신내역 DB기록
        if(Config.getHistDbYn().equals("Y") && !resultLists.isEmpty())
            new ProcBbdata().srHst2DB(resultLists, "S", Config.getJdbcDriver(), Config.getJdbcUrl(), Config.getJdbcUser(), Config.getJdbcPwd());
    }

    public void resultTreat(List<DtoFileList> resultLists){
        BufferedWriter bw = null;
        String rtnFname = "";
        for (DtoFileList resultList : resultLists) {
            try {
                //성공분 처리
                if(resultList.isRetYn()){
                    if(Config.getFileNameTp().equals("KEDU")){
                        //데이터파일이동
                        moveDataFile(resultList.getFilePath(), Config.getSendOkDir(), "");
                    }
                    else {
                        //리턴파일생성
                        rtnFname = resultList.getFilePath().replace("ABRQ", "ABRP")+".OK";
                        bw = new BufferedWriter(new FileWriter(rtnFname));
                        bw.close();
                        log.debug("[resultTreat] "+rtnFname);
                        //데이터파일이동
                        moveDataFile(resultList.getFilePath(), Config.getUsedDir(), "."+Util.getCurDtTm().substring(8));
                    }
                }
                //실패분 처리
                else{
                    if(Config.getFileNameTp().equals("KEDU")){
                        //데이터파일이동
                        moveDataFile(resultList.getFilePath(), Config.getSendFailDir(), "");
                    }
                    else {
                        //리턴파일생성
                        rtnFname = resultList.getFilePath().replace("ABRQ", "ABRP")+".FAIL";
                        bw = new BufferedWriter(new FileWriter(rtnFname));
                        bw.close();
                        log.debug("[resultTreat] "+rtnFname);
                    }

                }
            } catch (IOException e) {
                if(bw!=null) try {bw.close();} catch (IOException ignored) {}
                log.error(e.toString());
                e.printStackTrace();
            }
        }

    }

    public void moveDataFile(String srcFpath, String desDir, String extraTail){
        Path srcPath = Paths.get(srcFpath);
        Path desDirPath = Paths.get(desDir);
        String desFpath = desDir+System.getProperty("file.separator")+srcPath.getFileName().toString()+extraTail;
        Path desPath = Paths.get(desFpath);
        log.debug("[moveDataFile] => "+desFpath);

        try {
            if(!Files.exists(desDirPath))   Files.createDirectory(desDirPath);
            Files.move(srcPath, desPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error(e.toString());
            e.printStackTrace();
        }
    }

    public void setSndFile(File file, List<DtoFileList> sndDataLists){
        String fname = file.getName();
        String fileFlag="", sendDt="", sendCd="", recvCd="", infoCd="", fileSeq="";
        try {
            //K-edufine type
            if(Config.getFileNameTp().equals("KEDU")){
                FnmTpKEduFine fnmTpKEduFine = new FnmTpKEduFine(fname);
                fileFlag    = fnmTpKEduFine.getFileFlag().trim();
                sendDt      = fnmTpKEduFine.getSendDt().trim();
                sendCd      = fnmTpKEduFine.getSendCd().trim();
                recvCd      = fnmTpKEduFine.getRecvCd().trim();
                infoCd      = fnmTpKEduFine.getInfoCd().trim();
                fileSeq     = fnmTpKEduFine.getFileSeq();
            }
            else{
                FnmTpKsnet fnmTpKsnet = new FnmTpKsnet(fname);
                fileFlag    = fnmTpKsnet.getFileFlag().trim();
                sendDt      = fnmTpKsnet.getSendDt().trim();
                sendCd      = fnmTpKsnet.getSendCd().trim();
                recvCd      = fnmTpKsnet.getRecvCd().trim();
                infoCd      = fnmTpKsnet.getInfoCd().trim();
                fileSeq     = fnmTpKsnet.getFileSeq();
            }
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
            log.error(e.toString());
            /*2025-06-30 추가*/
            log.error("[setSndFile] abnormal file name ["+fname+"]");
            return;
        }
        //과거의 메세지파일 삭제
        if(fileFlag.equals("BRP")){
            file.delete();
            return;
        }
        //파일명규칙 점검
        log.debug("[setSndFile] "+fname);
        //송신대상파일인지 점검
        if(!fileFlag.equals(FnmTpKsnet.fFlagReq)){
            log.error("[setSndFile] no subject file : "+fileFlag);
            return;
        }
        //송신대상일자파일인지 점검
        String curDt = Util.getCurDtTm().substring(0,8);
        if(!sendDt.equals(curDt)){
            log.error("[setSndFile] not workdate file "+sendDt);
            return;
        }
        //아직 쓰고있는중인 파일인지 점검
        long timeGap = System.currentTimeMillis() - file.lastModified();
        if(timeGap < 60*1000L){
            log.debug("[setSndFile] probably still writing in the file...");
            return;
        }

        //송신자의 통신비번 검색
        String hpnPwd = "";
        for(int i=0 ; i<Config.getHyphenId().length ; i++){
            if(Config.getHyphenId()[i].equals(sendCd))
                try {hpnPwd = Config.getHyphenPwd()[i];}catch (Exception e){hpnPwd = "";}
        }

        //송신목록에 추가
        DtoFileList dtoFileList = new DtoFileList(sendDt, infoCd, sendCd, recvCd, fileSeq, file.getAbsolutePath(), false, hpnPwd);
        sndDataLists.add(dtoFileList);
    }



}
