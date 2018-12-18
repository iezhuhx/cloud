每个目录都有application文件
project/application文件  7771
project/config/application文件 7772
project/classes/application文件 7773
project/classes/config/application文件 7774

1 所有配置文件都存在的情况下：读取project/config下边的application*文件 启动的7772端口
2 去掉config中的启动文件application，读取的是project/application文件


配置文件优先级 ： 7772-7771-774-7773