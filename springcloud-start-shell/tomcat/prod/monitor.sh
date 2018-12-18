#!/bin/bash
PATH=/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin:/usr/java/jdk/bin
export JAVA_HOME=/usr/jdk
export CLASSPATH=$JAVA_HOME/lib:$JAVA_HOME/lib/tools.jar
export PATH=$PATH:$JAVA_HOME/bin
TomcatID=$(ps -ef |grep tomcat |grep -w 'apache-tomcat-7.0.52'|grep -v 'grep'|awk '{print $2}')
tomcat_home=/chenyb/apache-tomcat-7.0.52
StartTomcat=$tomcat_home/bin/startup.sh
TomcatCache=$tomcat_home/work
WebUrl=http://localhost:80
GetPageInfo=$tomcat_home/TomcatMonitor.Info
TomcatMonitorLog=$tomcat_home/TomcatMonitor.log
Monitor()
{
  echo "[info] start check tomcat...[$(date +'%F %H:%M:%S')]"
  if [ $TomcatID ]
  then
    echo "[info]current tomcat process ID is $TomcatID"
    echo "Test web service url is $WebUrl"
    TomcatServiceCode=$(curl -s -o $GetPageInfo -m 10 --connect-timeout 10 $WebUrl -w %{http_code})
    if [ $TomcatServiceCode -eq 200 ]
    then
        echo "[info]response code is $TomcatServiceCode,tomcat running is  normally......"
    else 
        echo "[error] service $WebUrl occur exception,return state is $TomcatServiceCode,please look log file at $GetPageInfo"
        echo "[error]page is wrong ,try to restart tomcat,kill tomcat at $TomcatID"
        kill -9 $TomcatID
        sleep 3
        rm -rf $TomcatCache
        $StartTomcat
    fi
  else 
    echo "[error]tomcat process not exist,prepare to restart..."
    echo "[info]$StartTomcat,wait for a moment......"
    rm -rf $TomcatCache
    $StartTomcat
  fi
  echo "------------------------------"
}
Monitor>>$TomcatMonitorLog
