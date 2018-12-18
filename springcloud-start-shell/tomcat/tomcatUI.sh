#!/bin/bash
exitFlag=1
tomcat_home=/chenyb/apache-tomcat-7.0.52
StartTomcat=$tomcat_home/bin/startup.sh
ShutDownTomcat=$tomcat_home/bin/shutdown.sh
echo " start cmd $StartTomcat"
echo " shutdown cmd $ShutDownTomcat"
run(){
  echo "************************************************"
  echo "*************1***********启动tomcat*************"
  echo "*************2***********关闭tomcat*************"
  echo "*************3***********重启tomcat*************"
  echo "*************4***********查看日志并退出*********"
  echo "*************5************退出管理界面**********"
  echo "************************************************"
  read -p 'please input cmd 1-5: ' cmd
  echo 'the cmd is '$cmd
  if [[ $cmd -eq 1 ]]
  then
     TomcatID=$(ps -ef |grep tomcat |grep -w 'apache-tomcat-7.0.52'|grep -v 'grep'|awk '{print $2}')
     if [ $TomcatID ]
     then
      echo 'find a tomcat serivce,process id is' $TomcatID
     else
      echo "启动tomcat..."
      $StartTomcat
     fi
  elif [[ $cmd -eq 2 ]]
  then
     TomcatID=$(ps -ef |grep tomcat |grep -w 'apache-tomcat-7.0.52'|grep -v 'grep'|awk '{print $2}')
     if [ $TomcatID ]
     then
      echo 'find a tomcat serivce,process id is' $TomcatID
      echo "关闭tomcat..."
      $ShutDownTomcat
     else
      echo "没有发现tomcat服务，无需关闭."
     fi
  elif [[ $cmd -eq 3 ]]
  then
     echo "重启tomcat..."
     $ShutDownTomcat
     sleep 5s
     $StartTomcat
     sleep 10s
  elif [[ $cmd -eq 4 ]]
  then
     echo "查看日志并退出..."
     tail -f $tomcat_home/logs/catalina.out
     exitFlag=0
  elif [[ $cmd -eq 5 ]]
  then
     echo "退出管理界面..."
     exitFlag=0
  else
     echo '命令不正确，请输入1-5之间的整数！'
  fi	 
}
echo "cur flag is $exitFlag"
while [[ $exitFlag -eq 1 ]]
do
 run
done
echo 'you has exit !'
