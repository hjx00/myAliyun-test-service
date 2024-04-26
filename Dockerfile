# 使用官方的 OpenJDK 8作为基础镜像
FROM openjdk:8

# 设置镜像维护者的信息（通常建议使用 LABEL 指令代替 MAINTAINER）
LABEL maintainer="1303955165@qq.com"

# 设置时区为中国上海
RUN echo "Asia/Shanghai" > /etc/timezone && \
    ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    dpkg-reconfigure --frontend noninteractive tzdata

# 设置镜像的描述信息
LABEL description="myAliyun-test-service"

# 将本地的 JAR 文件添加到镜像中，并重命名为 demo-test.jar
ADD myAliyun-test-service-1.0-SNAPSHOT.jar /app/myAliyun-test-service.jar

# 设置容器工作目录
WORKDIR /app

# 暴露8080端口
EXPOSE 8080

# 设置容器启动后的命令
# "-Dspring.profiles.active=dev",
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-server", "-XX:+UseG1GC","-XX:MaxGCPauseMillis=200","-Xmx1024m","-Xms1024m","-Xmn384m","-jar", "myAliyun-test-service.jar"]
