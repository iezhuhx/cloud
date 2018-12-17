@echo off
set curpath=%cd%
echo %curpath%
set env=dev
set projectname=zuul-1.0
rem set JAVA_HOME=
rem JMX监控需用到
set JMX="-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=1091 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false"
rem JVM参数
set JVM_OPTS="-Dname=$APP_NAME -Dspring.profiles.active="%env%" -Duser.timezone=Asia/Shanghai -Xms512M -Xmx512M -XX:PermSize=256M -XX:MaxPermSize=512M -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDateStamps -Xloggc:$GC_LOG_PATH -XX:+PrintGCDetails -XX:NewRatio=1 -XX:SurvivorRatio=30 -XX:+UseParallelGC -XX:+UseParallelOldGC"
rem 进入构建目录
cd %curpath%/target
rem 启动项目
echo %projectname%项目开始启动......
java -jar %JVM_OPTS% %JMX% %curpath%/target/%projectname%.jar
