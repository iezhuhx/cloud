#read -t 5 -p "������Ҫ�رյĶ˿ں�:" port
#echo -e "\n"
echo "������Ķ˿ں�Ϊ:$1"
echo -e "\n"
port=$1
lsof -i:$port|awk '{if (NR>1){print $2}}'
pid=`lsof -i:$port|awk '{if (NR>1){print $2}}'`
#if [ ! -n "$pid" ]; then
#if [ "$pid" = "" ]; then
if test -z "$pid"; then
 echo "�˿ں�$port������!"
else
  echo "���ڶ˿ں�port,���̺�$pid,���ڹرն˿ں�..."
  sleep 3s
fi