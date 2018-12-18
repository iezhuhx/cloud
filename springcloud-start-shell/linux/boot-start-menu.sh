# kconfig:   - 20 80
# description: Starts and stops the App.
# author:vakinge
#dev test prod
ENV=test
RUNNING_USER=tomcat
ADATE=`date +%Y%m%d%H%M%S`
APP_NAME=test-service
tomcatport=2081
jmxport=1082
APP_HOME=`pwd`
dirname $0|grep "^/" >/dev/null
if [ $? -eq 0 ];then
   APP_HOME=`dirname $0`
else
    dirname $0|grep "^\." >/dev/null
    retval=$?
    if [ $retval -eq 0 ];then
        APP_HOME=`dirname $0|sed "s#^.#$APP_HOME#"`
    else
        APP_HOME=`dirname $0|sed "s#^#$APP_HOME/#"`
    fi
fi

if [ ! -d "$APP_HOME/logs" ];then
  mkdir $APP_HOME/logs
fi

LOG_PATH=$APP_HOME/logs/$APP_NAME.out
GC_LOG_PATH=$APP_HOME/logs/gc-$APP_NAME-$ADATE.log
#JMX监控需用到
JMX="-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=$jmxport -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false"
#JVM参数
JVM_OPTS="-Dserver.port=$tomcatport -Dname=$APP_NAME -Dspring.profiles.active=$ENV -Duser.timezone=Asia/Shanghai -Xms512M -Xmx512M -XX:PermSize=256M -XX:MaxPermSize=512M -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDateStamps -Xloggc:$GC_LOG_PATH -XX:+PrintGCDetails -XX:NewRatio=1 -XX:SurvivorRatio=30 -XX:+UseParallelGC -XX:+UseParallelOldGC"

JAR_FILE=$APP_NAME.jar
pid=0
start(){
  checkpid
  if [ ! -n "$pid" ]; then
    JAVA_CMD="nohup java -jar $JVM_OPTS $JMX `pwd`/$JAR_FILE>$LOG_PATH 2>&1 &"
    su - $RUNNING_USER -c "$JAVA_CMD"
    echo "---------------------------------"
    echo "启动完成，按CTRL+C退出日志界面即可>>>>>"
    echo "$JAVA_CMD"
    pwd
    echo "---------------------------------"
    sleep 2s
    tail -f $LOG_PATH
  else
      echo "$APP_NAME is runing PID: $pid"   
  fi

}
status(){
   checkpid
   if [ ! -n "$pid" ]; then
     echo "$APP_NAME not runing"
   else
     echo "$APP_NAME runing PID: $pid"
   fi 
}
checkpid(){
    pid=`ps -ef |grep $JAR_FILE |grep -v grep |awk '{print $2}'`
}
stop(){
    checkpid
    if [ ! -n "$pid" ]; then
     echo "$APP_NAME not runing"
    else
      echo "$APP_NAME stop..."
      kill -9 $pid
    fi 
}
restart(){
    stop 
    sleep 1s
    start
}
function menu ()
{
    cat << EOF
----------------------------------------
|***************操作主菜单***************|
----------------------------------------
`echo -e "\033[35m 1)状态检查\033[0m"`
`echo -e "\033[35m 2)启动服务\033[0m"`
`echo -e "\033[35m 3)停止服务\033[0m"`
`echo -e "\033[35m 4)重启服务\033[0m"`
`echo -e "\033[35m 5)清    屏\033[0m"`
`echo -e "\033[35m 6)退    出\033[0m"`
EOF
read -p "请输入对应操作代码：" num
case $num in
    1)
      echo "状态检查..."
      status
      menu
      ;;
    2)
      echo "启动服务..."
      start
      menu
      ;;
   3)
     echo "停止服务..."
     stop
     sleep 1s
     echo "服务"$APP_NAME"停止成功！"
     menu
     ;;
   4)
     echo "重启服务..."
     restart
     menu
     ;;
   5)
      clear
      menu
      ;;
   6)
      exit 0
      ;;
   *) 
      echo "你输入的操作代码不正确，请检查！"
      menu
esac
}
menu
