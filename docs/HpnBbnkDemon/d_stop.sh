#!/usr/bin/ksh

##KSNET CODE(HYPHEN���� �߱޹��� ID)
KSNET_CD=A001
##��ġ���
INSTALL_HOME=/home/hyphen/HpnBbnkDemon
PROC_NAME=HpnBbnkDemon_$HYPHEN_ID
##Demon kill
ps -ef | grep $PROC_NAME  | grep -v 'grep' | awk '{print $2}' | xargs kill
