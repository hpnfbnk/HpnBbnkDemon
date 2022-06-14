#!/usr/bin/ksh

##HYPHEN_ID(HYPHEN���� �߱޹��� ID)
HYPHEN_ID=A001
##java��ġ��� (java7 �̻� �ʿ�)
JAVA_HOME=/usr/java7_64
##HYPHEN��� ��ġ���
INSTALL_HOME=/home/hyphen/HpnBbnkDemon
##Process �̸�
PROC_NAME=HpnBbnkDemon_$HYPHEN_ID
#######Daemon Process start 
nohup $JAVA_HOME/bin/java -D$PROC_NAME -jar $INSTALL_HOME/HpnBbnkDemon-1.1.0-jar-with-dependencies.jar 1>/dev/null 2>$INSTALL_HOME/log/err.log &
