check_port() {
        echo "���ڼ��˿ڡ�����"
        netstat -tlpn | grep "\b$1\b"
}
#$0 �ű���  $n n>0 ����ڼ�������
if check_port $1
then
    echo "�˿ڴ���"
    exit 1
else
    echo "�˿�����"
    DATE_N=`date "+%Y-%m%d %H:%M:%S"`
    echo "ʱ�䣺${DATE_N}" >> check_port.log #��¼������־
fi