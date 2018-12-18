#read -t 5 -p "请输入要关闭的端口号:" port
#echo -e "\n"
echo "你输入的端口号为:$1"
echo -e "\n"
port=$1
lsof -i:$port|awk '{if (NR>1){print $2}}'
pid=`lsof -i:$port|awk '{if (NR>1){print $2}}'`
#if [ ! -n "$pid" ]; then
#if [ "$pid" = "" ]; then
if test -z "$pid"; then
 echo "端口号$port不存在!"
else
  echo "存在端口号port,进程号$pid,正在关闭端口号..."
  sleep 3s
fi