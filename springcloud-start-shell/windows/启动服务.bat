@echo off
echo ��ӭʹ��FollowHeart�Զ������ߣ�%username% ....
echo ʹ��֮ǰ��ȷ��mongo��redis��װ��ַ���Ѿ����뵽����ϵͳ����������
ECHO.
:: ������������ start
If %username% == Andyliwr (
  :: �ǵøĳ�ANDYLIWRTHS
  echo %computername%
  If %computername% == ANDYLIWRTHS ( 
     set projectAddr=C:\Users\Andyliwr\Documents\graduationDesign
     set mongoDbPath=D:\mongo\data
     set mongoLogPath=D:\mongo\log\mongo.log
     set mongoConfAddr=D:\mongo\mongod.conf
     set redisConfAddr=D:\redis\redis.windows.conf
  ) Else (
     set projectAddr=C:\Users\Andyliwr\Documents\graduationDesign
     set mongoDbPath=E:\mongo_install\data
     set mongoLogPath=E:\mongo_install\log\mongo.log
     set mongoConfAddr=E:\mongo_install\mongod.conf
     set redisConfAddr=E:\redis\redis.windows.conf
  )
) Else (
  If %username% == Greenovia (
     set projectAddr=G:\git��Ŀ\graduationDesign
     set mongoDbPath=F:\mongo\data
     set mongoLogPath=F:\mongo\log\mongo.log
     set mongoConfAddr=F:\mongo\mongod.conf
     set redisConfAddr=F:\redis\redis.windows.conf
  ) Else (
     echo ��ʹ���ı��༭����start.bar�ֶ����õ�ַ��
     :: �������㵱ǰ��FollowHeart��Ŀ¼��ַ:
     set projectAddr=xxx
     :: ������mongo���ݴ洢��ַ(����F:\mongo\data):
     set mongoDbPath=xxx
     :: ������mongo��־�ļ���ַ(����F:\mongo\log\mongo.log):
     set mongoLogPath=xxx
     :: ������mongo�����ļ��ĵ�ַ(����F:\mongo\mongod.conf):
     set mongoConfAddr=xxx
     :: ������mongo�����ļ��ĵ�ַ(����F:\redis\redis.windows.conf):
     set redisConfAddr=xxx
  )
)
:: ������������ end
::��������
rem cls
goto start
:start
    echo --------------------------------------------------
    echo --                FollowHeart����               --
    echo --  1.����redis                                 --
    echo --  2.����jmeter                                --
    echo --  3.����ע������                              --
    echo --  4.������������                              --
    echo --  5.����Ӧ�ü��                              --
    echo --  6.����ĳ��Ӧ��                              --
    echo --  7.END                                       --
    echo --------------------------------------------------
    ECHO.
    echo (����������ѡ���������)
    set /p ans=                   
    if %ans%==1 goto startMongoAndRedis
    if %ans%==2 goto startApi
    if %ans%==3 goto startRankReptile
    if %ans%==4 goto startBdReptile
    if %ans%==5 goto startWechat
    if %ans%==6 goto deploy
    if %ans%==7 goto END
:startMongoAndRedis
    echo echo ���뵱ǰ����Ŀ¼
    start "����ִ�нű������ϵ����µĿ���̨����" enter-cur-directory.bat %redisConfAddr% %mongoLogPath% %mongoDbPath%
    echo echo ִ�нű�����...
    ECHO.
    ECHO.
    goto start
:startApi
    ECHO.
    echo ����������˽ӿڣ���ȷ���������ӿ�֮ǰ�Ѿ�ִ�й�1��...
    start "������˽ӿ�" startApi.bat %projectAddr%
    echo ��˽ӿ������ɹ�...
    ECHO.
    ECHO.
    goto start
:startBdReptile
    ECHO.
    echo �������аٶ���������...
    start "�����ٶ���������" startBdReptile.bat %projectAddr%
    ECHO.
    ECHO.
    goto start
:startRankReptile
    ECHO.
    echo �����������а�����...
    start "�����ٶ���������" startRankReptile.bat %projectAddr%
    ECHO.
    ECHO.
    goto start
:startWechat
    ECHO.
    echo ��������΢��С����...
   start "����΢��С����" startWechat.bat %projectAddr%
    ECHO.
    ECHO.
    goto start
:deploy
    ECHO.
    echo ----ִ��static��Ŀ��ѹ����----
    :: ִ�е��߼�����--Ϊbat�ļ�����
    start static.bat %uatPath% %sourcePath% %sourceDisk%
    echo ----static��Ŀ��ѹ�������----
    goto start
:END
    ECHO.
    echo �˳����������
pause