rem @echo off
set ENV=test
set APP_NAME=service
set JMXPORT=1091
set logpattern = %date:~0,4%%date:~5,2%%date:~8,2%
set GC_LOG_PATH=d:/data/%logpattern%-gc-log.log
rem %date:~0,4%%date:~5,2%%date:~8,2%%time:~0,2%%time:~3,2%%time:~6,2% 
cd %cd%
cd target
rem JMX监控需用到
set JMX=-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=%JMXPORT% -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false
set JVM_OPTS=-Dname=%APP_NAME% -Dspring.profiles.active=%ENV% -Duser.timezone=Asia/Shanghai -Xms512M -Xmx512M -XX:PermSize=256M -XX:MaxPermSize=512M -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDateStamps -Xloggc:%GC_LOG_PATH% -XX:+PrintGCDetails -XX:NewRatio=1 -XX:SurvivorRatio=30 -XX:+UseParallelGC -XX:+UseParallelOldGC
java -jar %JVM_OPTS% service.jar