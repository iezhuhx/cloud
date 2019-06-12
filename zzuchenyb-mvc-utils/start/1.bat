@ECHO OFF

rem mode con cols=36 lines=20

rem color 2C

:menu

cls

echo .

echo 有什么想对 就爱懒蛇 说的么？

echo ==============================

echo.

echo 输入1，说我爱你

echo.

echo 输入2，说我想你

echo.

echo 输入3，说加我QQ

echo.

echo ==============================

echo.

echo.

set /p user_input=请输入数字：

if %user_input% equ 1 echo 我爱你

if %user_input% equ 2 echo 我想你

if %user_input% equ 3 echo 加我QQ

pause

goto menu