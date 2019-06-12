@echo off
cd %cd%
REM 声明采用UTF-8编码
rem chcp 65001
echo 当前目录%cd%
java -Djava.ext.dirs=./lib  -cp ../target/mvc40.jar com.cyb.app.bms.BmsDataCheckWorker
pause