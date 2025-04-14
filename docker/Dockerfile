FROM openjdk:17-jdk
LABEL maintainer="rongxingyang@outlook.com"
# 创建应用目录
WORKDIR /app

# 将应用 JAR 包复制到容器
COPY ./gonav-server.jar app.jar

# 暴露容器的端口
EXPOSE 8080

# 设置容器启动时执行的命令
ENTRYPOINT ["java", "-jar", "app.jar"]