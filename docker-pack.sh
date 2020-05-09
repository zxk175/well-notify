#!/bin/bash

# 任何语句的执行结果不是true 则退出
set -e

# 打包
gradle :notify-admin:build -x test --no-daemon

rm -rf ./docker/*.jar

cp -R ./notify-admin/build/libs/*.jar ./docker/app.jar

# docker构建镜像
cd docker && docker build -f ./Dockerfile . -t zxk175/well-notify:1.0.0

# 停止容器
docker stop "$(docker ps -a | grep 'Exited' | awk '{print $1 }')"
# 删除已停止容器
docker rm "$(docker ps -a | grep 'Exited' | awk '{print $1 }')"
# 删除 none 镜像
docker rmi "$(docker images | grep 'none' | awk '{print $3}')"