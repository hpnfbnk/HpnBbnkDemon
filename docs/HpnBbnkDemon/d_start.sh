#!/usr/bin/ksh

##HYPHEN_ID(HYPHEN���� �߱޹��� ID)
HYPHEN_ID=A002
##java��ġ��� (java7 �̻� �ʿ�)
JAVA_HOME=/usr/java7_64
##HYPHEN��� ��ġ���
INSTALL_HOME=/home/hyphen/HpnBbnkDemon
##Process �̸�
PROC_NAME=HpnBbnkDemon_$HYPHEN_ID
#######Daemon Process start 
nohup $JAVA_HOME/bin/java -D$PROC_NAME -jar $INSTALL_HOME/HpnBbnkDemon-1.4.0-jar-with-dependencies.jar 1>/dev/null 2>$INSTALL_HOME/log/err.log &

##DB������� �Ʒ� start script ���
#JDBC_PATH=$INSTALL_HOME/ojdbc14.jar
#nohup $JAVA_HOME/bin/java -D$PROC_NAME -cp $INSTALL_HOME/HpnBbnkDemon-1.4.0-jar-with-dependencies.jar:$JDBC_PATH com.hyphen.fbnk.bbnkdemon.HpnBbnkDemon 1>/dev/null 2>$INSTALL_HOME/log/err.log &


