#!/bin/bash

# 定义变量
APP_DIR=/var/jenkins_home/workspace/myAliyun-test-service
APP_NAME=myaliyun-test-service
APP_PORT=8080
VERSION=1.0-SNAPSHOT

# 切换到应用目录
cd "$APP_DIR"
rm -f "myAliyun-test-service-$VERSION.jar"

# 复制新的jar包到应用目录
cp /var/jenkins_home/workspace/myAliyun-test-service/target/myAliyun-test-service-"${VERSION}".jar .

# 等待3秒确保文件复制完成
 sleep 3s

## 检查进程是否存在
# 使用awk提取docker ps命令中的PID，并排除包含"grep"的行，只取第二行
PID=$(docker ps -a --filter name="$APP_NAME" | grep -v "grep" | awk 'NR==2{print $1}')

# 检查PID是否为空
if [ -z "$PID" ]; then
    echo "应用：$APP_NAME 容器不存在。"
else
    echo "应用：$APP_NAME 存在容器：$PID"
    docker stop "$PID"
    docker rm "$PID"
    echo "容器：$PID 停止成功。"
    # 注意：docker rmi 是用来删除镜像的，不是用来删除容器的，这里应该是个错误
    # 如果你确实需要删除镜像，你需要先确定镜像的名称或ID，而不是容器的PID
    # docker rmi $IMAGE_NAME_OR_ID
    # echo "镜像：$IMAGE_NAME_OR_ID 删除成功。"
    # 上面的命令需要替换成正确的镜像名称或ID
fi

echo "应用：$APP_NAME 正在启动..."

# 构建Docker镜像，注意这里需要指定Dockerfile的位置，否则docker build可能不知道如何构建
# 例如：docker build -t $APP_NAME . （假设当前目录有Dockerfile）
docker buildx build -t "$APP_NAME":latest .

# 运行Docker容器
# 注意：这里需要确保映射的端口没有被其他容器占用，并且卷挂载的路径是存在的
docker run -m 4g --name "$APP_NAME" -d -p "$APP_PORT:$APP_PORT" -v /etc/localtime:/etc/localtime -v /mysortware/jenkins/workspace/myAliyun-test-service/data/logs:/app/data/logs "$APP_NAME"

echo "应用启动命令已执行，稍候请检查服务是否可用。"
echo
echo "====================end restart docker containers======================"
