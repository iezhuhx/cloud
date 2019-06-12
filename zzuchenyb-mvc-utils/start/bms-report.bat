@echo off
rem 给定时任务里边执行时，当前目录为c:/system/ 只能指定到绝对目录中。
d:
cd D:\chenyb\myproject\zhuhx\cloud\zzuchenyb-mvc-utils\start
REM 声明采用UTF-8编码
rem chcp 65001
echo 当前目录%cd%
java -Djava.ext.dirs=./lib  -cp ../target/mvc40.jar com.cyb.app.bms.BmsDataCheckWorker
pause