#!/bin/sh
# func:自动监控tomcat脚本并且执行重启操作
# author:iechenyb
# date:31/01/2018
# 定义环境变量
PATH=/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin:/usr/java/jdk/bin
export JAVA_HOME=/usr/jdk
export CLASSPATH=$JAVA_HOME/lib:$JAVA_HOME/lib/tools.jar
export PATH=$PATH:$JAVA_HOME/bin
# DEFINE
# 获取tomcat进程ID
TomcatID=$(ps -ef |grep tomcat |grep -w 'apache-tomcat-5.5.23'|grep -v 'grep'|awk '{print $2}')
# tomcat启动程序(这里注意tomcat实际安装的路径)
tomcat_home=/chenyb/apache-tomcat-7.0.52
StartTomcat=$tomcat_home/bin/startup.sh
TomcatCache=$tomcat_home/work
# 定义要监控的页面地址 
WebUrl=http://localhost:8080
# 日志输出
GetPageInfo=$tomcat_home/TomcatMonitor.Info
TomcatMonitorLog=$tomcat_home/TomcatMonitor.log
Monitor()
{
  echo "[info]开始监控tomcat...[$(date +'%F %H:%M:%S')]"
  if[ $TomcatID ];then  # 这里判断TOMCAT进程是否存在
    echo "[info]当前tomcat进程ID为:$TomcatID,继续检测页面..."
    # 检测是否启动成功(成功的话页面会返回状态"200")
    TomcatServiceCode=$(curl -s -o $GetPageInfo -m 10--connect-timeout 10 $WebUrl -w %{http_code})
    if[ $TomcatServiceCode -eq 200];then
        echo "[info]页面返回码为$TomcatServiceCode,tomcat启动成功,测试页面正常......"
    else
        echo "[error]tomcat页面出错,请注意......状态码为$TomcatServiceCode,错误日志已输出到$GetPageInfo"
        echo "[error]页面访问出错,开始重启tomcat"
        kill -9 $TomcatID  # 杀掉原tomcat进程
        sleep 3
        rm -rf $TomcatCache # 清理tomcat缓存
        $StartTomcat
    fi
  else
    echo "[error]tomcat进程不存在!tomcat开始自动重启..."
    echo "[info]$StartTomcat,请稍候......"
    rm -rf $TomcatCache
    $StartTomcat 
  fi
  echo "------------------------------"
}
Monitor>>$TomcatMonitorLog