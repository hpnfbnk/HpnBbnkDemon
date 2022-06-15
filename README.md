# HYPHEN BatchFirmBanking Demon Module
하이픈-배치펌뱅킹서비스용 연동Demon모듈입니다.

## 개요
주기적으로 지정된 송신파일보관경로의 지정된 파일명으로 된 송신대상파일을 읽어 HYPHEN으로 송신합니다.

주기적으로 HYPHEN에 수신요청을 해 수신대상파일을 지정된 수신파일보관경로에 지정된 파일명으로 수신합니다. 

## 요구사항
JAVA 1.7 이상을 요구합니다.

## 설치
[HpnBbnkDemon-1.1.0.tar.gz](https://hpnfbnk.github.io/HpnBbnkDemon/HpnBbnkDemon-1.1.0.tar.gz) 을 다운받아 압축해제하면 됩니다.

## 환경설정
### Logging (logback.xml)
log기록을 위해 logback.xml 의 log파일경로 부분을 적절히 지정합니다.
```xml
<appender name="file" class="ch.qos.logback.core.rolling.RollingFileAppender">
  <file>./log/HpnBbnkDemon.log</file>
  <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
    <fileNamePattern>./log/HpnBbnkDemon.%d{yyyyMMdd}.log</fileNamePattern>
    ...
<root level="DEBUG">
    ...
```
[debug] level 시 : 주요 세부동작단계에서 logging

[trace] level 시 : 실제로 Hyphen server와 주고받는 전문까지 모두 logging

### 환경변수 (hpnbbnk.properties)
환경변수 설정을 위해 hpnbbnk.properties 을 적절히 설정합니다.
```properties
#HYPHEN에서 발급받은 ID (콤마로 구분시 복수ID 사용가능. 예:A001,A002)
hyphenId=A001
#송신파일 보관경로
sendDir=/home/anno/HpnBbnkDemon/send
#송신완료파일 보관경로
usedDir=/home/anno/HpnBbnkDemon/send/usedfile
...
```
* logback.xml 과 hpnbbnk.properties 는 HpnBbnkDemon*.jar 와 같은 디렉토리에 있어야합니다.

## 사용법
### Unix, Linux 계열
#### Demon 시작 (d_start.sh)
d_start.sh 시작스크립트의 내용을 적절히 편집한후 실행시킵니다.
```shell
##HYPHEN_ID(HYPHEN에서 발급받은 ID)
HYPHEN_ID=A001
##java설치경로(java7 이상 필요)
JAVA_HOME=/usr/java7_64
##HYPHEN모듈 설치경로
INSTALL_HOME=/home/anno/HpnBbnkDemon
...
```
#### Demon 종료(d_stop.sh)
d_stop.sh 종료스크립트의 내용을 적절히 편집한후 실행시킵니다.
```shell
##HYPHEN_ID(HYPHEN에서 발급받은 ID)
HYPHEN_ID=A001
##HYPHEN모듈 설치경로
INSTALL_HOME=/home/anno/HpnBbnkDemon
...
```
### Windows 계열
#### Service 등록 (winReg.bat)
winReg.bat 서비스등록 스크립트를 적절히 편집한후 **관리자권한으로** 실행시킵니다.
```shell
@set HYPHEN_ID=A001
@set INSTALL_HOME=C:\HpnBbnkDemon
@set JRE_HOME=C:\Java\jdk1.8.0_261\jre
@rem OS bit에 따라 JavaService_64bit 또는 JavaService_32bit 사용
@set JAVASERVICE=%INSTALL_HOME%\javaService\JavaService_64bit.exe
...
```
#### Service 실행 (winStart.bat)
winStart.bat 서비스실행 스크립트를 적절히 편집한후 **관리자권한으로** 실행시킵니다.
```shell
@set HYPHEN_ID=A001
...
```
#### Service 중지 (winStop.bat)
winStop.bat 서비스중지 스크립트를 적절히 편집한후 관리자권한으로 실행시킵니다.
```shell
@set HYPHEN_ID=A001
...
```
#### Service 제거 (winUnreg.bat)
```shell
@set HYPHEN_ID=A001
@set INSTALL_HOME=C:\HpnBbnkDemon
@rem OS bit에 따라 JavaService_64bit 또는 JavaService_32bit 사용
@set JAVASERVICE=%INSTALL_HOME%\javaService\JavaService_64bit.exe
...
```

## 파일명 및 파일처리 규칙
### 파일명타입:기본 (fileNameTp=DFLT)
hpnbbnk.properties 내의 fileNameTp 값이 기본(DFLT)인 경우
#### 송신파일명 규칙
[문자셋구분(1)][업무종류 구분(3)][송신일자(8)]_[송신자코드(4)][수신자코드(4)][파일종류구분(3)].[순번]
* 문자인코딩타입 : A=ascii, E=ebcdic
* 업무종류 구분 : HYPHEN으로 송신할파일은 파일명에 **BRQ**라는 구분자를 가지고 있어  야합니다.
  (BRQ라는 구분자가 없는것은 송신대상파일이 아니라고 판단해 송신업무에서 제외됩니다.)
* 송신일자 : 송신일자가 당일인것만 송신대상파일이라고 취급합니다.
* 송신자코드 : 파일을 보내는측의 HYPHEN_ID (A001, 1234 등...)
* 수신자코드 : 파일을 받을기관의 HYPHEN_ID(0081:하나은행, 0084:우리은행, 0240:삼성증권, 0997:HYPHEN배치대행서버, 0998:HYPHEN통합처리서버 등..)
* 파일종류구분 : 송신파일의 종류(200:집금, 300:지급, R00:계좌등록, Y00:증빙자료,I02:집금-통합, I03:지급-통합, I0R:계좌등록-통합, IY0:증빙자료-통합,A02:집금-대행, A0R:계좌등록-대행, AY0:증빙자료-대행,Y01:계좌변경, Y02:계좌변경결과, Y03:해지통보, Y05:증빙자료사후점검,Y06:증빙자료사후점검정보, C01:법인카드승인내역 등..)
* 순번 : 동일한 수신자에게 동일한종류의 파일을 여러개보낼때 순번으로 파일구분.
* 예) ABRQ20170623_A0010004200.001 => 6월23일에 A001이라는 업체가 국민은행(0004)으로 보내는 출금요청(200) 파일
#### 송신처리결과파일 규칙 
[문자셋구분(1)][업무종류 구분(3)][송신일자(8)]_[송신자코드(4)][수신자코드(4)][파일종류구분(3)].[순번].[결과코드]
* 업무종류 구분 : 송신처리결과파일은 송신파일과 동일한 파일명에 "파일종류 구분자"만 **BRP**로 바뀝니다.(송신처리결과파일은 다음번 송신감시업무 주기 때 삭제됩니다.)
* 결과코드 : 해당송신파일의 송신작업결과를 나타냅니다.(OK:성공, FAIL:실패)
* 예) ABRQ20171011_A0010004200.001.OK : 10월11일에 A001이라는 업체가 국민은행(0004)으로 보내는 출금요청(200) 파일 송신성공(OK)
* 예) ABRQ20171011_A0010004200.002.FAIL : 10월11일에 A001이라는 업체가 국민은행(0004)으로 보내는 출금요청(200) 파일 송신실패(FAIL)
#### 송신완료파일 처리규칙  
송신에 성공한 파일은 지정된 송신완료파일 보관경로(usedDir)에 송신성공시간을 파일명뒤에 붙여 보관됩니다.
* 예) ABRQ20220602_A0010081200.001.105832
#### 수신파일명 규칙
[문자셋구분(1)][업무종류 구분(3)][수신일자(8)]_[송신자코드(4)][수신자코드(4)][파일종류구분(3)].[순번(3)]
* 문자인코딩타입 : A=ascii, E=ebcdic
* 업무종류 구분 : HYPHEN에서 수신된파일은 **BRR**이라는 구분자를 가지고있습니다.
* 수신일자 : HYPHEN으로부터 수신된 일자입니다.
* 송신자코드 : 파일을 보낸 기관의 HYPHEN_ID 입니다. (0081:하나은행, 0084:우리은행, 1126:삼성증권 등..).
* 수신자코드 : 파일을 받을 업체의 HYPHEN_ID(A000, 1234 등...) 입니다.
* 파일종류구분 : 수신파일의 종류(R00:계좌등록, 200:집급이체요청, 300:지급이체요청 등..).
* 순번 : 수신파일명이 동일할시 순번으로 구분.
* 예) ABRR20171011_0004A001200.001 => 10월11일에 국민은행(0004)으로 부터 A001에게 온 출금결과(200) 파일
### 파일명타입:K-에듀파인용 (fileNameTp=KEDU)
K-에듀파인용 파일명타입 규칙에 따릅니다.

## DB 연계
법인카드사용내역(C01~C06)의 경우 준비된 TABLE에 INSERT시킬수도 있습니다.
* 첨부된 [TableScheme.sql](https://hpnfbnk.github.io/HpnBbnkDemon/HpnBbnkDemon/TableScheme.sql) 을 참조해 각업무별 TABLE들을 만들어 놓습니다.
* hpnbbnk.properties 의 cocaDbYn값을 Y로 하고 사용하실 DB환경에 따라 jdbc관련값(jdbcUser, jdbcPwd, jdbcDriver, jdbcUrl)들을 설정합니다.
* 사용하실 DB환경에 알맞은 JDBC 라이브러리(mysql-connector-java-5.1.6-bin.jar, ojdbc14.jar 등..)를 classpath에 포함시킵니다.
* 법인카드사용내역파일이 수신되면 그내역이 각업무별 table에 insert될것입니다. 