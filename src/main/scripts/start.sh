#!/bin/bash
cd `dirname $0`
BIN_DIR=`pwd`
cd ..
DEPLOY_DIR=`pwd`
CONF_DIR=$DEPLOY_DIR

PIDS=`ps -f | grep java | grep "$CONF_DIR" |awk '{print $2}'`
if [ -n "$PIDS" ]; then
    echo "ERROR: The $DEPLOY_DIR already started!"
    echo "PID: $PIDS"
    exit 1
fi

LOGS_DIR=$DEPLOY_DIR/logs

export CLS_BSS_HOME=$DEPLOY_DIR

if [ ! -d $LOGS_DIR ]; then
    mkdir $LOGS_DIR
fi
STDOUT_FILE=$LOGS_DIR/cls-bss.log

LIB_DIR=$DEPLOY_DIR/lib
LIB_JARS=`ls $LIB_DIR|grep .jar|awk '{print "'$LIB_DIR'/"$0}'|tr "\n" ":"`

JAVA_OPTS=" -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true "
GC_OPTS=" -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:$DEPLOY_DIR/var/log/gc.log "
JAVA_DEBUG_OPTS=""
if [ "$1" = "debug" ]; then
    JAVA_DEBUG_OPTS=" -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=6000,server=y,suspend=n "
fi
JAVA_JMX_OPTS=""
if [ "$1" = "jmx" ]; then
    JAVA_JMX_OPTS=" -Dcom.sun.management.jmxremote.port=1099 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false "
fi
JAVA_MEM_OPTS=""
BITS=`java -version 2>&1 | grep -i 64-bit`
if [ -n "$BITS" ]; then
    JAVA_MEM_OPTS=" -server -Xmx2g -Xms2g -Xmn256m -XX:PermSize=128m -Xss256k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 "
else
    JAVA_MEM_OPTS=" -server -Xms1g -Xmx1g -XX:PermSize=128m -XX:SurvivorRatio=2 -XX:+UseParallelGC "
fi

DUMP_ON_OUT_OF_MEM=""
if ["$1" = "oome" ]; then
    DUMP_ON_OUT_OF_MEM=" -XX:-UseGCOverheadLimit -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$LOGS_DIR -XX:ErrorFile=$LOGS_DIR/hs_error_%p.log "
fi

echo "/usr/java/jdk1.8.0_66/bin/java $JAVA_OPTS $DUMP_ON_OUT_OF_MEM $JAVA_MEM_OPTS $JAVA_DEBUG_OPTS $JAVA_JMX_OPTS -Djava.ext.dirs=$LIB_DIR -cp $CONF_DIR:$CONF_DIR/lib:$CONF_DIR/conf:$CONF_DIR/conf/spring:$CONF_DIR/conf/dubbo-consumer:$CONF_DIR/conf/dubbo-provider\c"
echo -e "Starting the $DEPLOY_DIR ...\c"
nohup /usr/java/jdk1.8.0_66/bin/java $JAVA_OPTS $GC_OPTS $DUMP_ON_OUT_OF_MEM $JAVA_MEM_OPTS $JAVA_DEBUG_OPTS $JAVA_JMX_OPTS -Djava.ext.dirs=$LIB_DIR -cp $CONF_DIR:$CONF_DIR/lib:$CONF_DIR/conf:$CONF_DIR/conf/spring:$CONF_DIR/conf/dubbo-consumer:$CONF_DIR/conf/dubbo-provider com.cgw360.cls.bss.util.DubboStarter >> $STDOUT_FILE 2>&1 &

COUNT=0
while [ $COUNT -lt 1 ]; do    
    echo -e ".\c"
    sleep 1 
    COUNT=`ps -f | grep java | grep "$DEPLOY_DIR" | awk '{print $2}' | wc -l`
    if [ $COUNT -gt 0 ]; then
        break
    fi
done

echo "OK!"
PIDS=`ps -f | grep java | grep "$DEPLOY_DIR" | awk '{print $2}'`
echo "PID: $PIDS"
echo "STDOUT: $STDOUT_FILE"
