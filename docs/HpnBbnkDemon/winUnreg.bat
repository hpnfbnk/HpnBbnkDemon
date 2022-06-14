@set HYPHEN_ID=A001
@set INSTALL_HOME=C:\itjProject\HpnBbnkDemon\docs\HpnBbnkDemon
@set SVC_NAME=HpnBbnkDemon_%HYPHEN_ID%
@rem OS bit에 따라 JavaService_64bit 또는 JavaService_32bit 사용
@set JAVASERVICE=%INSTALL_HOME%\javaService\JavaService_64bit.exe

%JAVASERVICE% -uninstall %SVC_NAME%
@pause
