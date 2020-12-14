@echo off
:: rem when exe as a cron task ,the dir  must be c:/system/ !。
d:
cd D:\chenyb\myproject\zhuhx\cloud\zzuchenyb-mvc-utils\start\crm-report-check
echo cur dir is %cd%
java -Djava.ext.dirs=./dependency  -cp ../../target/mvc40.jar com.cyb.app.webAgent.AutoWebHello
pause