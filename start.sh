#!/bin/bash

#参考地址：http://www.jianshu.com/p/1903cd80223c
#Mac权限修改：chmod 777 start.sh
#Linux权限修改：chmod a+x start.sh

#0、删除原有的日志文件
rm -f ./nohup.log

#1、从 git 上拉取最新的代码
git pull

#2、使用 gradle 打包最新的代码 跳过测试
#gradle build -x test --no-daemon --stacktrace
gradle build -x test --stacktrace

#3、获取正在运行的 Spring Boot 应用的 pid
pid=`ps -ef|grep well-admin-*.jar|grep -v grep|awk '{print $2}'`

#4、关闭正在运行的 Spring Boot 应用
kill -9 ${pid}

#5、后台运行新的 jar 文件
#nohup java -Xmx600m -jar well-admin/build/libs/well-admin-*.jar > nohup.log 2>&1 &
nohup java -jar well-admin/build/libs/well-admin-*.jar > nohup.log 2>&1 &

#6、休息 3 秒
sleep 3

#7、打印最新的日志
tail -f ./nohup.log