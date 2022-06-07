#!/usr/bin/ksh

##HYPHEN_ID(HYPHEN에서 발급받은 ID)
HYPHEN_ID=A001
##java설치경로 (java7 이상 필요)
JAVA_HOME=/usr/java7_64
##HYPHEN모듈 설치경로
INSTALL_HOME=/home/hyphen/HpnBbnkDemon
##Process 이름
PROC_NAME=HpnBbnkDemon_$HYPHEN_ID
#######Daemon Process start 
nohup $JAVA_HOME/bin/java -D$PROC_NAME -jar $INSTALL_HOME/HpnBbnkDemon-1.0.1-jar-with-dependencies.jar 1>/dev/null 2>$INSTALL_HOME/log/err.log &
