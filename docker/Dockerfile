FROM openjdk:8u212-jre-alpine
LABEL maintainer="zxk175 zxk175@qq.com"

WORKDIR app
ADD ./app.jar .

# 查看目录
#RUN pwd && ls -al

#-Xms jvm启动时分配的内存，比如-Xms200m，表示分配200M
#-Xmx jvm运行过程中分配的最大内存，比如-Xms500m，表示jvm进程最多只能够占用500M内存
ENV JAVA_OPTS="-Xms256m -Xmx384m -Djava.security.egd=file:/dev/./urandom"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar app.jar" ]

