package com.hyphen.fbnk.bbnkdemon;

import com.hyphen.fbnk.bbnk.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

public class Config {
    private static final Logger log = LoggerFactory.getLogger(Config.class);
    private static Properties properties ;
    private static String[] hyphenId = null;

    private static String sendDir = "";
    private static String usedDir = "";
    private static String sendOkDir = "";
    private static String sendFailDir = "";
    private static String recvDir = "";
    private static String recvDataTp = "ZZZ";
    private static String fromDate = "-7";
    private static String toDate = "today";
    private static String reqTp = "E";
    private static String recvCd = "999";
    private static int monitTerm = 10;
    private static boolean delnYn = false;
    private static boolean zipYn = false;
    private static String netBps = "2M";
    private static String fileNameTp = "DFLT";
    private static String runMode = "T";
    private static String customIpYn = "N";
    private static String hpnSvrIpProd = "121.138.30.10";
    private static String hpnSvrIpTest = "121.138.30.31";
    private static int hpnSvrPort = 29994;
    private static String hpnSvrIpPrf = "121.138.30.10";
    private static int hpnSvrPortPrf = 29995;
    private static String cocaDbYn = "N";
    private static String jdbcUser = "";
    private static String jdbcPwd = "";
    private static String jdbcDriver = "";
    private static String jdbcUrl = "";
    private static String[] hyphenPwd = null;

    public Config(){}
    public static void setConfig() {
        properties = new Properties();
        String resource = "hpnbbnk.properties";
        try {
            properties.load(new FileInputStream(resource));
        } catch (IOException e) {
            try {
                resource = Config.class.getResource("/").getPath()+resource;
                properties.load(new FileInputStream(resource));
            } catch (IOException ex) {
                log.error(ex.toString());
                ex.printStackTrace();
            }
        }
        log.debug("resource="+resource);

        hyphenId        = get("hyphenId").split(",");
        sendDir         = get("sendDir");
        usedDir         = get("usedDir");
        sendOkDir       = get("sendOkDir");
        sendFailDir     = get("sendFailDir");
        recvDir         = get("recvDir");
        recvDataTp      = get("recvDataTp");
        toDate          = get("toDate").equals("today") ? Util.getCurDtTm().substring(0, 8) : get("toDate") ;
        int fromChk     = Integer.parseInt(get("fromDate"));
        fromDate        = fromChk < 100 ? spanDate(toDate, fromChk) : get("fromDate");
        reqTp           = get("reqTp");
        recvCd          = get("recvCd");
        monitTerm       = Integer.parseInt(get("monitTerm"));
        delnYn          = get("delnYn").equals("Y");
        zipYn           = get("zipYn").equals("Y");
        netBps          = get("netBps");
        fileNameTp      = get("fileNameTp");
        runMode         = get("runMode");
        customIpYn      = get("customIpYn");
        hpnSvrIpProd    = get("hpnSvrIpProd");
        hpnSvrIpTest    = get("hpnSvrIpTest");
        hpnSvrPort      = Integer.parseInt(get("hpnSvrPort"));
        hpnSvrIpPrf     = get("hpnSvrIpPrf");
        hpnSvrPortPrf   = Integer.parseInt(get("hpnSvrPortPrf"));
        cocaDbYn        = get("cocaDbYn");
        jdbcUser        = get("jdbcUser");
        jdbcPwd         = get("jdbcPwd");
        jdbcDriver      = get("jdbcDriver");
        jdbcUrl         = get("jdbcUrl");
        hyphenPwd       = get("hyphenPwd").split(",");

        for (String hpnId : hyphenId)   log.debug("hyphenId="+hpnId);
        log.debug("sendDir="+sendDir+", usedDir="+usedDir+", recvDir="+recvDir+", recvDataTp="+recvDataTp+
                ", fromDate="+fromDate+", toDate="+toDate+", reqTp="+reqTp+", monitTerm="+monitTerm+", delnYn="+delnYn+ ", zipYn="+zipYn+
                ", netBps="+netBps+", fileNameTp="+fileNameTp+", runMode="+runMode+", customIpYn="+customIpYn);
        if(customIpYn.equals("Y"))
            log.debug("hpnSvrIpProd="+hpnSvrIpProd+", hpnSvrIpTest="+hpnSvrIpTest+", hpnSvrPort="+hpnSvrPort+ ", hpnSvrIpPrf="+hpnSvrIpPrf+
                    ", hpnSvrPortPrf="+hpnSvrPortPrf);
        if(fileNameTp.equals("KEDU"))
            log.debug("sendOkDir="+sendOkDir+", sendFailDir="+sendFailDir);
        if(cocaDbYn.equals("Y"))
            log.debug("cocaDbYn="+cocaDbYn+", jdbcDriver="+jdbcDriver+", jdbcUrl="+jdbcUrl);
    }

    public static String spanDate(String yyyymmdd, int span){
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(yyyymmdd.substring(0,4)), Integer.parseInt(yyyymmdd.substring(4,6))-1, Integer.parseInt(yyyymmdd.substring(6,8)));

        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        cal.add(Calendar.DATE, span);

        return format.format(cal.getTime());
    }

    public static String get(String keyName){
        return properties.getProperty(keyName);
    }

    public static String[] getHyphenId() {
        return hyphenId;
    }

    public static String getSendDir() {
        return sendDir;
    }

    public static String getUsedDir() {
        return usedDir;
    }

    public static String getSendOkDir() {
        return sendOkDir;
    }

    public static String getSendFailDir() {
        return sendFailDir;
    }

    public static String getRecvDir() {
        return recvDir;
    }

    public static String getRecvDataTp() {
        return recvDataTp;
    }

    public static String getFromDate() { return fromDate; }

    public static String getToDate() {
        return toDate;
    }

    public static String getReqTp() {
        return reqTp;
    }

    public static String getRecvCd() {
        return recvCd;
    }

    public static int getMonitTerm() {
        return monitTerm;
    }

    public static boolean isDelnYn() {
        return delnYn;
    }

    public static boolean isZipYn() { return zipYn; }

    public static String getNetBps() {
        return netBps;
    }

    public static String getFileNameTp() {
        return fileNameTp;
    }

    public static String getRunMode() {
        return runMode;
    }

    public static String getCustomIpYn() {
        return customIpYn;
    }

    public static String getHpnSvrIpProd() {
        return hpnSvrIpProd;
    }

    public static String getHpnSvrIpTest() {
        return hpnSvrIpTest;
    }

    public static int getHpnSvrPort() {
        return hpnSvrPort;
    }

    public static String getHpnSvrIpPrf() {
        return hpnSvrIpPrf;
    }

    public static int getHpnSvrPortPrf() {
        return hpnSvrPortPrf;
    }
    public static String getCocaDbYn() {return cocaDbYn;}
    public static String getJdbcUser() {return jdbcUser;}
    public static String getJdbcPwd() {return jdbcPwd;}
    public static String getJdbcDriver() {return jdbcDriver;}
    public static String getJdbcUrl() {return jdbcUrl;}

    public static String[] getHyphenPwd() {
        return hyphenPwd;
    }
}
