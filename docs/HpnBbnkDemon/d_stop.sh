#!/usr/bin/ksh

##KSNET CODE(HYPHEN에서 발급받은 ID)
KSNET_CD=A001
##설치경로
INSTALL_HOME=/home/hyphen/HpnBbnkDemon
PROC_NAME=HpnBbnkDemon_$HYPHEN_ID
##Demon kill
ps -ef | grep $PROC_NAME  | grep -v 'grep' | awk '{print $2}' | xargs kill
