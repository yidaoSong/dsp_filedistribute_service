#!/bin/sh
MAIN_CLASS=com.bfd.monitor.ApiFileDistributeService
BASE_DIR=$(cd $(dirname $0)/..; pwd)
MYLIB=$BASE_DIR/lib
CLASSPATH=$BASE_DIR/conf:$MYLIB/*
echo $CLASSPATH
export LOG4J_HOME=$BASE_DIR
echo " the program is starting..."
case $1 in
    debug)
    echo "start with debug mode..."
    java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8001,suspend=n -Xms512m -Xmx2024m -cp $CLASSPATH $MAIN_CLASS > $BASE_DIR/logs/main.log 2>&1 &;;
    *)
    echo "start with default mode..."
    java  -Xms512m -Xmx2024m -cp $CLASSPATH $MAIN_CLASS > $BASE_DIR/logs/main.log 2>&1 &;;
esac