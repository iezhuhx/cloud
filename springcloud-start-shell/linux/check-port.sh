check_port() {
        echo "正在检测端口。。。"
        netstat -tlpn | grep "\b$1\b"
}
#$0 脚本名  $n n>0 代表第几个参数
if check_port $1
then
    echo "端口存在"
    exit 1
else
    echo "端口死亡"
    DATE_N=`date "+%Y-%m%d %H:%M:%S"`
    echo "时间：${DATE_N}" >> check_port.log #记录死亡日志
fi