@ECHO OFF

rem mode con cols=36 lines=20

rem color 2C
REM 声明采用UTF-8编码
rem chcp 65001
:menu

cls
cd %cd%
echo 当前目录%cd%
echo .

echo Choice the program to start

echo ==============================

echo.

echo 1	BMS-REPORT

echo.

echo 2	CSDN

echo.

echo 3	STOCK
echo.

echo 4	HOLIDAY
echo.

echo 5	GlobalTaskWindow

echo.

echo ==============================

echo.

echo.

set /p user_input=please input the number:
echo  your %user_input%

pause

goto menu