@set HYPHEN_ID=A001
@set INSTALL_HOME=C:\itjProject\HpnBbnkDemon\docs\HpnBbnkDemon
@set JRE_HOME=C:\Java\jdk1.8.0_261\jre
@rem OS bit에 따라 JavaService_64bit 또는 JavaService_32bit 사용
@set JAVASERVICE=%INSTALL_HOME%\javaService\JavaService_64bit.exe

@set SVC_NAME=HpnBbnkDemon_%HYPHEN_ID%
@set CLASSPATH=%INSTALL_HOME%;%INSTALL_HOME%\HpnBbnkDemon-1.1.0-jar-with-dependencies.jar
@rem @set CLASSPATH=%INSTALL_HOME%;%INSTALL_HOME%\HpnBbnkDemon-1.1.0-jar-with-dependencies.jar;%INSTALL_HOME%\mysql-connector-java-5.1.44-bin.jar

%JAVASERVICE% -install %SVC_NAME% "%JRE_HOME%"\bin\server\jvm.dll -Djava.class.path=%CLASSPATH% -start com.hyphen.fbnk.bbnkdemon.HpnBbnkDemon -out %INSTALL_HOME%\log\out.log -err %INSTALL_HOME%\log\err.log
@pause
