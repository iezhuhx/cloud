# spring-boot-with-activiti
整合工作流
activiti version 5.22.0
spring boot version 1.5.9.RELEASE
mysql5.0+
mybatis

#文件中心

1. 通过git下载activiti源码：https://github.com/Activiti/Activiti
2. 切换分支到5.22
    git checkout activiti-5.22.0
3. 参考引用http://jmysql.com/activiti/126.html
4. 作者博客地址： https://blog.csdn.net/las723

#相关接口

1.首页 http://localhost

2.我发起的记录 http://localhost/history/process/mys?userId=c00776c0-186c-11e9-b046-e58aa127f5e6

3.我待办的记录 http://localhost/runtime/tasks/ing?userId=bee0dbc0-1956-11e9-bde4-5516b71b463e

4.我操作的记录 http://localhost/history/tasks/myc?userId=bee0dbc0-1956-11e9-bde4-5516b71b463e

#使用说明

1.将源码down到本地，用idea或eclipse打开，会初始化maven依赖，直到成功

2.新建数据库，更改src/main/resources/application.yml对应配置信息

3.初始化src/main/resources/db-init.sql表结构，自己新加几条数据做测试用

4.启动ActivitiApplication.java

5.访问首页即可，

#注意事项

1.因业务需要使用自己环境的用户权限；

2.配置流程图时，流程名称须填、流程key必须唯一
    流程开始节点id必须配置为S00000，当然你也可以根据代码修改



--------------------- 
作者：艾斯-李 
来源：CSDN 
原文：https://blog.csdn.net/las723/article/details/88417152 
版权声明：本文为博主原创文章，转载请附上博文链接！