# Docker一键部署

## 一、安装Docker, DockerCompose

### 0. 更新系统

```
sudo apt update
sudo apt upgrade -y
```

### 1. 卸载旧版本（如果有）

```
sudo apt remove docker docker-engine docker.io containerd runc
```

### 2. 安装必要依赖

```
sudo apt install -y \
    ca-certificates \
    curl \
    gnupg \
    lsb-release
```

### 3. 添加 Docker 的 GPG 密钥

```
sudo mkdir -p /etc/apt/keyrings

curl -fsSL https://download.docker.com/linux/ubuntu/gpg | \
    sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
```

### 4. 添加 Docker 软件源

```
echo \
  "deb [arch=$(dpkg --print-architecture) \
  signed-by=/etc/apt/keyrings/docker.gpg] \
  https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
```

### 5. 安装 Docker 引擎

```
sudo apt update
sudo apt install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
```

> ✅ 安装完成后，你可以通过以下命令验证：

```
docker version
docker info
```

这样你就不需要每次运行 docker 命令时都加 `sudo`：

```
sudo usermod -aG docker $USER
```

然后**重新登录一次或重启系统**使其生效。

### 6. 安装docker-compose

下载最新版本

```java
sudo curl -L "https://github.com/docker/compose/releases/download/v2.27.0/docker-compose-$(uname -s)-$(uname -m)" \
    -o /usr/local/bin/docker-compose
```

添加可执行权限

```
sudo chmod +x /usr/local/bin/docker-compose
```

验证安装

```
docker-compose version
```

测试是否安装成功

```
docker run hello-world
```

## 二、部署项目

克隆项目

```
git clone https://github.com/dawnstaryrx/go-nav.git
```

进入docker目录

```
cd go-nav/docker
```

修改`docker-compose.yml`中的信息，和`nginx/nginx.conf`的ip

```
vim docker-compose.yml
vim nginx/nginx.conf
```

启动

```
docker-compose up -d
```

关闭

```
docker-compose down
```



