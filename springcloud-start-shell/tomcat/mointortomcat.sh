#!/bin/sh
# func:�Զ����tomcat�ű�����ִ����������
# author:iechenyb
# date:31/01/2018
# ���廷������
PATH=/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin:/usr/java/jdk/bin
export JAVA_HOME=/usr/jdk
export CLASSPATH=$JAVA_HOME/lib:$JAVA_HOME/lib/tools.jar
export PATH=$PATH:$JAVA_HOME/bin
# DEFINE
# ��ȡtomcat����ID
TomcatID=$(ps -ef |grep tomcat |grep -w 'apache-tomcat-5.5.23'|grep -v 'grep'|awk '{print $2}')
# tomcat��������(����ע��tomcatʵ�ʰ�װ��·��)
tomcat_home=/chenyb/apache-tomcat-7.0.52
StartTomcat=$tomcat_home/bin/startup.sh
TomcatCache=$tomcat_home/work
# ����Ҫ��ص�ҳ���ַ 
WebUrl=http://localhost:8080
# ��־���
GetPageInfo=$tomcat_home/TomcatMonitor.Info
TomcatMonitorLog=$tomcat_home/TomcatMonitor.log
Monitor()
{
  echo "[info]��ʼ���tomcat...[$(date +'%F %H:%M:%S')]"
  if[ $TomcatID ];then  # �����ж�TOMCAT�����Ƿ����
    echo "[info]��ǰtomcat����IDΪ:$TomcatID,�������ҳ��..."
    # ����Ƿ������ɹ�(�ɹ��Ļ�ҳ��᷵��״̬"200")
    TomcatServiceCode=$(curl -s -o $GetPageInfo -m 10--connect-timeout 10 $WebUrl -w %{http_code})
    if[ $TomcatServiceCode -eq 200];then
        echo "[info]ҳ�淵����Ϊ$TomcatServiceCode,tomcat�����ɹ�,����ҳ������......"
    else
        echo "[error]tomcatҳ�����,��ע��......״̬��Ϊ$TomcatServiceCode,������־�������$GetPageInfo"
        echo "[error]ҳ����ʳ���,��ʼ����tomcat"
        kill -9 $TomcatID  # ɱ��ԭtomcat����
        sleep 3
        rm -rf $TomcatCache # ����tomcat����
        $StartTomcat
    fi
  else
    echo "[error]tomcat���̲�����!tomcat��ʼ�Զ�����..."
    echo "[info]$StartTomcat,���Ժ�......"
    rm -rf $TomcatCache
    $StartTomcat 
  fi
  echo "------------------------------"
}
Monitor>>$TomcatMonitorLog