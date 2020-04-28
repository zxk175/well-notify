FROM gradle:6.3.0-jdk8 as builder
LABEL maintainer="zxk175 zxk175@qq.com"

ADD . app

# 查看目录
#RUN pwd && ls -al app

WORKDIR app

RUN gradle :notify-admin:build -x test --no-daemon \
    && cd notify-admin/build/libs/ && mv *.jar app.jar


FROM openjdk:8u212-jre-alpine
LABEL maintainer="zxk175 zxk175@qq.com"

COPY --from=builder /home/gradle/app/notify-admin/build/libs/ .

# 查看目录
#RUN ls -al

ENV JAVA_OPTS="-Xmx300m -Xms300m -Djava.security.egd=file:/dev/./urandom"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar app.jar" ]
