#!/bin/bash

# 验证.env是否有效
#docker-compose -f dc-app.yml config
# 删除容器
#docker-compose -f dc-app.yml down --remove-orphans

gradle task clear

gradle :well-core:build -x test

gradle :well-admin:build -x test

docker-compose -f dc-app.yml up -d --build