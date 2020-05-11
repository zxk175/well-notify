#!/bin/bash

# 任何语句的执行结果不是true 则退出
set -e

docker_username="zxk175@qq.com"
docker_server_addr=registry.cn-shenzhen.aliyuncs.com

docker_tag="1.0.0"
docker_project="zxk175/well-notify"
docker_image_local="${docker_project}:1.0.0"
docker_image_latest="${docker_project}:latest"

docker images

docker login -u${docker_username} ${docker_server_addr}

# push 指定tag分支
docker tag ${docker_image_local} ${docker_server_addr}/${docker_project}:${docker_tag}
docker push ${docker_server_addr}/${docker_project}:${docker_tag}

# push latest分支
docker tag ${docker_image_local} ${docker_server_addr}/${docker_image_latest}
docker push ${docker_server_addr}/${docker_image_latest}

docker logout
