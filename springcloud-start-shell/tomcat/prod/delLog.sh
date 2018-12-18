#!/bin/bash
days=160
start=1
logpath=/chenyb/shell/log/
while [ $start -lt $days  ]
do
cur=$((days-start))
curd='Web-YH-'$(date -d "${cur} days ago" +%Y-%m-%d)'.log'
echo 'cur file name  is '$curd
if [[ -f $logpath$curd ]]
#! -f 表示不存在文件
then
rm $logpath$curd
echo 'remove '$logpath$curd' success!'
else
echo $logpath$curd' not fund!'
fi
start=$((start+1))

done
