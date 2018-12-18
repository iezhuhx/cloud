#!/bin/bash
PATH=/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin:/usr/java/jdk/bin
export JAVA_HOME=/usr/jdk
export CLASSPATH=$JAVA_HOME/lib:$JAVA_HOME/lib/tools.jar
export PATH=$PATH:$JAVA_HOME/bin
tomcatVersion='apache-tomcat-7.0.52'
name=chenyb
echo 'Hi '$name,'tomcat version is '$tomcatVersion
TomcatID=$(ps -ef |grep tomcat |grep -w 'apache-tomcat-7.0.52'|grep -v 'grep'|awk '{print $2}')
if [ $TomcatID ]
then
	echo 'find a tomcat serivce,process id is' $TomcatID
else
	echo 'no tomcat service fund !'
fi