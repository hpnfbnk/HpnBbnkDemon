# HYPHEN BatchFirmBanking Demon Module
������-��ġ�߹�ŷ���񽺿� ����Demon����Դϴ�.

## ����
�ֱ������� ������ �۽����Ϻ�������� ������ ���ϸ����� �� �۽Ŵ�������� �о� HYPHEN���� �۽��մϴ�.

�ֱ������� HYPHEN�� ���ſ�û�� �� ���Ŵ�������� ������ �������Ϻ�����ο� ������ ���ϸ����� �����մϴ�.

## �䱸����
JAVA 1.7 �̻��� �䱸�մϴ�.

## ��ġ
[HpnBbnkDemon-1.1.0.tar.gz](https://hpnfbnk.github.io/HpnBbnkDemon/HpnBbnkDemon-1.1.0.tar.gz) �� �ٿ�޾� ���������ϸ� �˴ϴ�.

## ȯ�漳��
### Logging (logback.xml)
log����� ���� logback.xml �� log���ϰ�� �κ��� ������ �����մϴ�.
```xml
<appender name="file" class="ch.qos.logback.core.rolling.RollingFileAppender">
  <file>./log/HpnBbnkDemon.log</file>
  <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
    <fileNamePattern>./log/HpnBbnkDemon.%d{yyyyMMdd}.log</fileNamePattern>
    ...
<root level="DEBUG">
    ...
```
[debug] level �� : �ֿ� ���ε��۴ܰ迡�� logging

[trace] level �� : ������ Hyphen server�� �ְ�޴� �������� ��� logging

### ȯ�溯�� (hpnbbnk.properties)
ȯ�溯�� ������ ���� hpnbbnk.properties �� ������ �����մϴ�.
```properties
#HYPHEN���� �߱޹��� ID (�޸��� ���н� ����ID ��밡��. ��:A001,A002)
hyphenId=A001
#�۽����� �������
sendDir=/home/anno/HpnBbnkDemon/send
#�۽ſϷ����� �������
usedDir=/home/anno/HpnBbnkDemon/send/usedfile
...
```
* logback.xml �� hpnbbnk.properties �� HpnBbnkDemon*.jar �� ���� ���丮�� �־���մϴ�.

## ����
### Unix, Linux �迭
#### Demon ���� (d_start.sh)
d_start.sh ���۽�ũ��Ʈ�� ������ ������ �������� �����ŵ�ϴ�.
```shell
##HYPHEN_ID(HYPHEN���� �߱޹��� ID)
HYPHEN_ID=A001
##java��ġ���(java7 �̻� �ʿ�)
JAVA_HOME=/usr/java7_64
##HYPHEN��� ��ġ���
INSTALL_HOME=/home/anno/HpnBbnkDemon
...
```
#### Demon ����(d_stop.sh)
d_stop.sh ���ὺũ��Ʈ�� ������ ������ �������� �����ŵ�ϴ�.
```shell
##HYPHEN_ID(HYPHEN���� �߱޹��� ID)
HYPHEN_ID=A001
##HYPHEN��� ��ġ���
INSTALL_HOME=/home/anno/HpnBbnkDemon
...
```
### Windows �迭
#### Service ��� (winReg.bat)
winReg.bat ���񽺵�� ��ũ��Ʈ�� ������ �������� **�����ڱ�������** �����ŵ�ϴ�.
```shell
@set HYPHEN_ID=A001
@set INSTALL_HOME=C:\HpnBbnkDemon
@set JRE_HOME=C:\Java\jdk1.8.0_261\jre
@rem OS bit�� ���� JavaService_64bit �Ǵ� JavaService_32bit ���
@set JAVASERVICE=%INSTALL_HOME%\javaService\JavaService_64bit.exe
...
```
#### Service ���� (winStart.bat)
winStart.bat ���񽺽��� ��ũ��Ʈ�� ������ �������� **�����ڱ�������** �����ŵ�ϴ�.
```shell
@set HYPHEN_ID=A001
...
```
#### Service ���� (winStop.bat)
winStop.bat �������� ��ũ��Ʈ�� ������ �������� �����ڱ������� �����ŵ�ϴ�.
```shell
@set HYPHEN_ID=A001
...
```
#### Service ���� (winUnreg.bat)
```shell
@set HYPHEN_ID=A001
@set INSTALL_HOME=C:\HpnBbnkDemon
@rem OS bit�� ���� JavaService_64bit �Ǵ� JavaService_32bit ���
@set JAVASERVICE=%INSTALL_HOME%\javaService\JavaService_64bit.exe
...
```

## ���ϸ� �� ����ó�� ��Ģ
### ���ϸ�Ÿ��:�⺻ (fileNameTp=DFLT)
hpnbbnk.properties ���� fileNameTp ���� �⺻(DFLT)�� ���
#### �۽����ϸ� ��Ģ
[���ڼ±���(1)][�������� ����(3)][�۽�����(8)]_[�۽����ڵ�(4)][�������ڵ�(4)][������������(3)].[����]
* �������ڵ�Ÿ�� : A=ascii, E=ebcdic
* �������� ���� : HYPHEN���� �۽��������� ���ϸ� **BRQ**��� �����ڸ� ������ �־�  ���մϴ�.
  (BRQ��� �����ڰ� ���°��� �۽Ŵ�������� �ƴ϶�� �Ǵ��� �۽ž������� ���ܵ˴ϴ�.)
* �۽����� : �۽����ڰ� �����ΰ͸� �۽Ŵ�������̶�� ����մϴ�.
* �۽����ڵ� : ������ ���������� HYPHEN_ID (A001, 1234 ��...)+69+
* �������ڵ� : ������ ��������� HYPHEN_ID(0081:�ϳ�����, 0084:�츮����, 0240:�Ｚ����, 0997:HYPHEN��ġ���༭��, 0998:HYPHEN����ó������ ��..)
* ������������ : �۽������� ����(200:����, 300:����, R00:���µ��, Y00:�����ڷ�,I02:����-����, I03:����-����, I0R:���µ��-����, IY0:�����ڷ�-����,A02:����-����, A0R:���µ��-����, AY0:�����ڷ�-����,Y01:���º���, Y02:���º�����, Y03:�����뺸, Y05:�����ڷ��������,Y06:�����ڷ������������, C01:����ī����γ��� ��..)
* ���� : ������ �����ڿ��� ������������ ������ ������������ �������� ���ϱ���.
* ��) ABRQ20170623_A0010004200.001 => 6��23�Ͽ� A001�̶�� ��ü�� ��������(0004)���� ������ ��ݿ�û(200) ����
#### �۽�ó��������� ��Ģ
[���ڼ±���(1)][�������� ����(3)][�۽�����(8)]_[�۽����ڵ�(4)][�������ڵ�(4)][������������(3)].[����].[����ڵ�]
* �������� ���� : �۽�ó����������� �۽����ϰ� ������ ���ϸ� "�������� ������"�� **BRP**�� �ٲ�ϴ�.(�۽�ó����������� ������ �۽Ű��þ��� �ֱ� �� �����˴ϴ�.)
* ����ڵ� : �ش�۽������� �۽��۾������ ��Ÿ���ϴ�.(OK:����, FAIL:����)
* ��) ABRQ20171011_A0010004200.001.OK : 10��11�Ͽ� A001�̶�� ��ü�� ��������(0004)���� ������ ��ݿ�û(200) ���� �۽ż���(OK)
* ��) ABRQ20171011_A0010004200.002.FAIL : 10��11�Ͽ� A001�̶�� ��ü�� ��������(0004)���� ������ ��ݿ�û(200) ���� �۽Ž���(FAIL)
#### �۽ſϷ����� ó����Ģ
�۽ſ� ������ ������ ������ �۽ſϷ����� �������(usedDir)�� �۽ż����ð��� ���ϸ�ڿ� �ٿ� �����˴ϴ�.
* ��) ABRQ20220602_A0010081200.001.105832
#### �������ϸ� ��Ģ
[���ڼ±���(1)][�������� ����(3)][��������(8)]_[�۽����ڵ�(4)][�������ڵ�(4)][������������(3)].[����(3)]
* �������ڵ�Ÿ�� : A=ascii, E=ebcdic
* �������� ���� : HYPHEN���� ���ŵ������� **BRR**�̶�� �����ڸ� �������ֽ��ϴ�.
* �������� : HYPHEN���κ��� ���ŵ� �����Դϴ�.
* �۽����ڵ� : ������ ���� ����� HYPHEN_ID �Դϴ�. (0081:�ϳ�����, 0084:�츮����, 1126:�Ｚ���� ��..).
* �������ڵ� : ������ ���� ��ü�� HYPHEN_ID(A000, 1234 ��...) �Դϴ�.
* ������������ : ���������� ����(R00:���µ��, 200:������ü��û, 300:������ü��û ��..).
* ���� : �������ϸ��� �����ҽ� �������� ����.
* ��) ABRR20171011_0004A001200.001 => 10��11�Ͽ� ��������(0004)���� ���� A001���� �� ��ݰ��(200) ����
### ���ϸ�Ÿ��:K-�������ο� (fileNameTp=KEDU)
K-�������ο� ���ϸ�Ÿ�� ��Ģ�� �����ϴ�.


## DB ����
����ī���볻��(C01~C06)�� ��� �غ�� TABLE�� INSERT��ų���� �ֽ��ϴ�.
* ÷�ε� [TableScheme.sql](https://hpnfbnk.github.io/HpnBbnkDemon/HpnBbnkDemon/logback.xml/TableScheme.sql) �� ������ �������� TABLE���� ����� �����ϴ�.
* hpnbbnk.properties �� cocaDbYn���� Y�� �ϰ� ����Ͻ� DBȯ�濡 ���� jdbc���ð�(jdbcUser, jdbcPwd, jdbcDriver, jdbcUrl)���� �����մϴ�.
* ����Ͻ� DBȯ�濡 �˸��� JDBC ���̺귯��(mysql-connector-java-5.1.6-bin.jar, ojdbc14.jar ��..)�� classpath�� ���Խ�ŵ�ϴ�.
* ����ī���볻�������� ���ŵǸ� �׳����� �������� table�� insert�ɰ��Դϴ�. 