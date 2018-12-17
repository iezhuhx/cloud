@echo off
set curpath=%cd%
echo %curpath%
set env=dev
set projectname=zuul-1.0
rem set JAVA_HOME=
rem JMX������õ�
set JMX="-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=1091 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false"
rem JVM����
set JVM_OPTS="-Dname=$APP_NAME -Dspring.profiles.active="%env%" -Duser.timezone=Asia/Shanghai -Xms512M -Xmx512M -XX:PermSize=256M -XX:MaxPermSize=512M -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDateStamps -Xloggc:$GC_LOG_PATH -XX:+PrintGCDetails -XX:NewRatio=1 -XX:SurvivorRatio=30 -XX:+UseParallelGC -XX:+UseParallelOldGC"
rem ���빹��Ŀ¼
cd %curpath%/target
rem ������Ŀ
echo %projectname%��Ŀ��ʼ����......
java -jar %JVM_OPTS% %JMX% %curpath%/target/%projectname%.jar
