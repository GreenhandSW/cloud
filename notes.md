# 知识

### 1. `DiscoveryClient`

`DiscoveryClient.getServices()`只能获取到自己所注册的`EurekaServer`上存在的服务。

```java
@PostMapping("/discovery")
public Object discovery(){
    // 获取服务列表的信息
    List<String> services=discoveryClient.getServices();
    for (String service :
            services) {
        log.info("******service: " + service);
    }
    // 获取所有实例
    List<ServiceInstance> instances=discoveryClient.getInstances("pay");
    for (ServiceInstance instance :
            instances) {
        log.info("{}\t{}:{}/{}", instance.getServiceId(), instance.getHost(), instance.getPort(), instance.getUri());
    }
    return discoveryClient;
}
```

### 2. EurekaServer的自我保护模式

表现形式：在eureka的主页会显示：<font color="red">EMERGENCY! EUREKA MAY BE INCORRECTLY CLAIMING INSTANCES ARE UP WHEN THEY'RE NOT. RENEWALS ARE LESSER THAN THRESHOLD AND HENCE THE INSTANCES ARE NOT BEING EXPIRED JUST TO BE SAFE.</font>

**自我保护模式**：保护服务注册表中的信息，不再主动注销任何客户端

**进入条件**：若EurekaServer节点在短世界内丢失过多客户端时（比如主动下线或者网络故障），则节点进入自我保护模式。

关闭方式：在Server节点中添加下面的配置

```properties
server:
  enable-self-preservation: false
```



# Debug

### 1. Provides transitive vulnerable dependency maven:org.yaml:snakeyaml:1.33

构建springboot项目时，`spring-boot-starter`依赖一直提示这个错误，于是在`dependencies`中添加如下依赖，把`snankeyaml`版本升级。

```xml
<dependency>
    <groupId>org.yaml</groupId>
    <artifactId>snakeyaml</artifactId>
    <version>2.2</version>
</dependency>
```

### 2. 在一个包里写的AOP在其他包里不起作用

1. 假设在a包里实现了切面，那么在实现切面的这个类上加扫描注解，路径就是这个类所在的包的全路径

   ```java
   @ComponentScan(basePackages = "io.github.greenhandsw.split")
   ```

2. 在需要使用这个切面的包（这里称为b包）的pom.xml文件里添加对a包的依赖

   ```java
           <dependency>
               <groupId>io.github.GreenhandSW</groupId>
               <artifactId>split</artifactId>
               <version>0.0.1-SNAPSHOT</version>
               <scope>compile</scope>
           </dependency>
   ```

3. 在b包的启动类上加导入注解，导入实现切面的类[^ 7]。

   ```java
   @Import(DataSourceInterceptor.class)
   ```

### 3. canal连接失败

目前canal-server一直无法连接到数据库，暂未解决。

已经解决，见[10](# 10. mysql-binlog-connector-java监听不到任何事件)。

### 4. `@Value`注解一直失效，显示找不到方法

导入错了，不能导入`lombok.Value`，必须导入`org.springframework.beans.factory.annotation.Value`。

### 5. JPA引用其他包的类一直显示Not a managed type: class

需要让Spring扫描到包，在启动类上添加实体扫描的注解，从组织名一直写到这个实体类所在的目录[^ 8]：

```java
@EntityScan("io.github.greenhandsw.core.entity")
public class PayApplication {}
```

### 6. 找不到RestTemplate的Bean

Spring1.4以后不提供默认的`RestTemplate`的Bean，需要手动注入：

1. 添加一个配置类[^ 9]：

   ```java
   @Component
   public class RestConfig {
       @Bean
       public RestTemplate restTemplate(){
           return new RestTemplate();
       }
   }
   ```

2. 在需要用到的地方注入：

   ```java
   @Resource
   private RestTemplate restTemplate;
   ```

### 7. docker的奇特bug

目前测试发现，如果给mysql和canal分别写两个`docker-compose.yml`文件，那么canal始终是连接不上mysql的。但是，如果把两个容器都写入同一个`docker-compose-yml`文件里，并且给mysql起名叫做`mysql`，在canal的`instance.properties`文件里把mysql的连接地址改为`canal.instance.master.address=mysql:3306`，就可以成功连接。而如果改为`localhost:3306`等等其他地址，就无法连接。

目前的猜测是，这个跟docker的network的设置有关。

灵感来源：[^ 12]。

解决办法：见[10](# 10. mysql-binlog-connector-java监听不到任何事件)。

### 8. canal始终连接不到mysql，一直报Connection Refused错误

1. 首先确保配置没问题。
2. 如果canal和mysql都是docker容器，那问题其实是因为docker容器之间通信机制的特性[^ 10]。不同容器之间通信的地址是服务名，比如如果compose的时候mysql服务名为a，canal服务名为b，那canal连接mysql需要把地址设置为a，也就是`canal.instance.master.address=a:3306`。

### 9. canal报错：PositionNotFoundException: can't find start position for example

canal连接的mysql服务的server-id不能为0[^ 11]，必须另外设置其他值。

### 10. mysql-binlog-connector-java监听不到任何事件

问题可能还是出在docker通信机制上。使用本地mysql是可以监听到的，但是docker mysql会导致`BinaryLogClient`一直重置连接：

> Trying to restore lost connection to localhost:3306

创建mysql容器的时候，应该这样：

1. 在外部创建一个子网：

   ```sh
   docker network create cloud
   ```

2. 在mysql的docker-compose.yml文件里声明外部网络：

   ```yaml
   networks:
     cloud:
       external: true
   ```

3. 把mysql的docker-compose.yml文件里的服务连接到子网：

   ```yaml
   services:
     mysql:
       networks:
         cloud: 
       # 省略其他配置
   ```

4. 接下来无论是canal还是mysql-binlog-connector-java都可以正常连接mysql了。

### 11. redis集群创建报错：ERR Invalid node address specified: node0:6379

因为`redis-cli`目前并不支持主机名，因此无论是多机之间或者是docker内的多节点之间，都不能使用类似于`hostname:port`的形式，只能使用`ip:port`的形式[^ 13]。

### 12. docker一直警告：WARNING! Your password will be stored unencrypted in /home/sw/.docker/config.json.

因为密码存在config.json里，不安全。

解决办法：

1. 从[docker-credential-helpers](https://github.com/docker/docker-credential-helpers/releases)下载一个helper，mac下载osxkeychain，linux下载pass或者secretservice，win下载wincred。

2. 把下载的文件移动到一个固定的文件夹里，比如`/opt/docker-credential`，然后在shell的配置文件里把这个目录添加到PATH，然后source配置文件刷新之。

   ```sh
   # 比如zsh就添加到~/.zshrc里
   export PATH=/opt/docker-credential:$PATH
   ```

3. 给这个文件可执行权限

   ```sh
   sudo chmod +x /path/to/the/helper
   ```

4. 创建一个软链接指向该文件（因为下载的文件后面带着版本号，但是docker只能识别形如docker-credential-xxxx的文件）：

   ```sh
   sudo ln -s /path/to/the/helper docker-credential-pass
   ```

5. 在`~/.docker/config.json`文件里写入使用的加密文件：

   ```json
   {
           "credsStore": "pass"
   }
   ```

   上一步得到的是docker-credential-xxxx文件就在这里填写xxxx，比如docker-credential-pass就填写pass。

6. 然后docker logout再docker login就没有烦人的warning了

# 配置

## redis cluster

### 1. 通过k8s实现（弃用）

1. 安装minikube及kubectl

   ```sh
   curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube_latest_amd64.deb
   sudo dpkg -i minikube_latest_amd64.deb
   
   # 启动minikube
   minikube start
   # 安装kubectl
   minikube kubectl -- get po -A
   ```

   配置alias添加到`~/.zshrc中`，然后`source ~/.zshrc`刷新[^ 1]。

   ```sh
   alias kubectl="minikube kubectl --"
   ```

2. 安装包管理器helm

   ```sh
   curl https://baltocdn.com/helm/signing.asc | gpg --dearmor | sudo tee /usr/share/keyrings/helm.gpg > /dev/null
   sudo apt-get install apt-transport-https --yes
   echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/helm.gpg] https://baltocdn.com/helm/stable/debian/ all main" | sudo tee /etc/apt/sources.list.d/helm-stable-debian.list
   sudo apt-get update
   sudo apt-get install helm
   ```

3. 添加redis镜像并启动一个redis实例

   ```sh
   helm repo add bitnami https://charts.bitnami.com/bitnami
   helm repo update
   # 第一个redis可以改成自己想要的任何名字
   helm install redis bitnami/redis
   # 启动图形界面（会自动打开浏览器），就能看到当前的实例
   minikube dashboard
   # 停止minikube
   minikube stop
   ```

4. idea配置kubernetes

   1. 在plugins里搜索kubernetes安装
   2. 在`File | Settings | Build, Execution, Deployment | Kubernetes`里设置kubectl和helm的路径，其中kubectl是通过minikube安装的，因此默认在`$HOME/.minikube/cache/linux/amd64/v1.27.4/kubectl`，当然版本可能变化，建议通过fsearch搜索路径。helm路径通过`which helm`搜索。

5. 填写k8s redis-cluster配置文件[^ 2]：

   ```yml
   # 创建存储类
   apiVersion: storage.k8s.io/v1
   kind: StorageClass
   metadata:
     name: local-storage
   provisioner: kubernetes.io/no-provisioner
   volumeBindingMode: WaitForFirstConsumer
   allowVolumeExpansion: true
   reclaimPolicy: Delete
   ---
   
   # 创建持久卷
   apiVersion: v1
   kind: PersistentVolume
   metadata:
     name: local-pv0
   spec:
     storageClassName: local-storage
     capacity:
       storage: 1Gi
     accessModes:
       - ReadWriteOnce
     hostPath:
       path: "./node0/data"
   ---
   
   apiVersion: v1
   kind: PersistentVolume
   metadata:
     name: local-pv1
   spec:
     storageClassName: local-storage
     capacity:
       storage: 1Gi
     accessModes:
       - ReadWriteOnce
     hostPath:
       path: "./node1/data"
   ---
   
   apiVersion: v1
   kind: PersistentVolume
   metadata:
     name: local-pv2
   spec:
     storageClassName: local-storage
     capacity:
       storage: 2Gi
     accessModes:
       - ReadWriteOnce
     hostPath:
       path: "./node2/data"
   ---
   
   apiVersion: v1
   kind: ConfigMap
   metadata:
     name: redis-cluster-configmap
   data:
     redis.conf: |-
       cluster-enabled yes
       cluster-require-full-coverage no
       cluster-node-timeout 15000
       cluster-config-file /data/nodes.conf
       cluster-migration-barrier 1
       appendonly yes
       requirepass cloud
       masteruser master
       protected-mode no
       bind 0.0.0.0
       port 6379
   ---
   
   
   apiVersion: apps/v1
   kind: StatefulSet
   metadata:
     name: redis-cluster
   spec:
     serviceName: redis-cluster
     replicas: 6
     selector:
       matchLabels:
         app: redis-cluster
     template:
       metadata:
         labels:
           app: redis-cluster
       spec:
         containers:
         - name: redis
           image: redis
           command: ["redis-server"]
           args: ["/conf/redis.conf"]
           env:
           - name: REDIS_CLUSTER_ANNOUNCE_IP
             valueFrom:
               fieldRef:
                 fieldPath: status.podIP
           ports:
           - containerPort: 6379
             name: client
           - containerPort: 16379
             name: gossip
           volumeMounts:
           - name: conf
             mountPath: /conf
           - name: data
             mountPath: /data
     volumeClaimTemplates:
     - metadata:
         name: data
       spec:
         accessModes: [ "ReadWriteOnce" ]
         resources:
           requests:
             storage: 1Gi
   ```

6. 暂时不搞了，太麻烦了。

### 2. 通过docker compose实现

1. 填写redis节点的配置文件模板，注意`$PORT`和`BUS_PORT`需要让脚本自动填写

   ```tmpl
   # 端口号
   port 6379
   
   # 日志级别，默认notice
   loglevel notice
   
   # 设置客户端连接后进行任何其他指定前需要使用的密码
   requirepass cloud
   
   ## 当master服务设置了密码保护时(用requirepass制定的密码)
   # slave服务连接master的密码
   masterauth cloud
   
   # daemonize no 将daemonize yes注释起来或者 daemonize no设置，因为该配置和docker run中-d参数冲突，会导致容器一直启动失败
   daemonize no
   
   # 任何主机都可以连接到redis
   bind 0.0.0.0
   
   # 是否开启保护模式，默认开启。要是配置里没有指定bind和密码。开启该参数后，redis只会本地进行访问，拒绝外部访问。
   protected-mode yes
   
   # 开启AOF
   appendonly yes
   
   # 集群开关，如果配置yes则开启集群功能，此redis实例作为集群的一个节点，否则，它是一个普通的单一的redis实例。
   cluster-enabled yes
   
   # 集群配置文件的名称，此配置文件不能人工编辑，它是集群节点自动维护的文件，主要用于记录集群中有哪些节点、他们的状态以及一些持久化参数等，方便在重启时恢复这些状态。通常是在收到请求之后这个文件就会被更新。
   cluster-config-file nodes.conf
   
   # 节点互连超时的阀值。集群节点超时毫秒数
   cluster-node-timeout 15000
   
   # 以下三个配置参数静态设定节点的运行参数, 包括对外IP, 普通Redis命令端口和集群桥接端口
   
   # Redis Cluster 不支持NATted环境和IP地址或TCP端口被重映射(remapped)的环境。
   # Docker使用一种名叫port mapping的技术, 运行于Docker容器内的程序实际对外使用的端口和监听的端口可不相同. 为了让Docker 兼容Redis Cluster, 需要使用Docker的host network模式。
   # 为了使Redis集群在这样的环境中工作，静态每个节点都知道需要其公共地址的配置
   cluster-announce-ip 172.17.0.1
   
   # 客户端连接端口
   cluster-announce-port ${PORT}
   
   # 总线端口为普通端口port+10000，所谓Cluster bus, 即使用一种二进制协议(binary protocol)进行集群内点对点(node-to-node)通讯, 包括节点失效检测, 配置更新, 故障转移(failover)认证等
   cluster-announce-bus-port ${BUS_PORT}
   ```

1. 创建`node.sh`脚本，生成每个节点的`redis.conf`配置文件：

   ```sh
   #!/bin/bash
   
   # Define the number of nodes you want to create
   num_nodes=6
   
   # Create the nodes
   for i in $(seq 0 $((num_nodes-1))); do
       node_dir="node$i"
       rm -rf $node_dir
       mkdir -p "$node_dir"/{data,conf}
       PORT=$((10000 + ${i})) BUS_PORT=$((PORT + 10000)) envsubst < redis-node.tmpl > $node_dir/conf/redis.conf
   #    touch "$node_dir"/conf/my.conf
   done
   ```

2. 填写docker-compose.yml配置文件[^ 3][^ 4]：

   ```yml
   version: '2'
   
   services:
     redis-cluster:
       image: redis:latest
       networks:
         redis:
           ipv4_address: 172.19.0.2
       command: redis-cli -a cloud --cluster create 172.19.0.11:6379 172.19.0.12:6379 172.19.0.13:6379 172.19.0.14:6379 172.19.0.15:6379 172.19.0.16:6379 --cluster-replicas 1  --cluster-yes
       depends_on:
         - node0
         - node1
         - node2
         - node3
         - node4
         - node5
   
     node0: # 服务名称
       image: redis:latest # 创建容器时所需的镜像
       container_name: node0 # 容器名称
       restart: "no" # 容器总是重新启动
       networks:
         redis:
           ipv4_address: 172.19.0.11
       ports:
         - "10000:6379"
         - "20000:16379"
       volumes: # 数据卷，目录挂载
         - ./etc_rc.local:/etc/rc.local
         - ./node0/conf:/etc/redis
         - ./node0/data:/data
       command: redis-server /etc/redis/redis.conf # 覆盖容器启动后默认执行的命令
     node1:
       image: redis:latest
       container_name: node1
       networks:
         redis:
           ipv4_address: 172.19.0.12
       ports:
         - "10001:6379"
         - "20001:16379"
       volumes:
         - ./etc_rc.local:/etc/rc.local
         - ./node1/conf:/etc/redis
         - ./node1/data:/data
       command: redis-server /etc/redis/redis.conf
     node2:
       image: redis:latest
       container_name: node2
       networks:
         redis:
           ipv4_address: 172.19.0.13
       ports:
         - "10002:6379"
         - "20002:16379"
       volumes:
         - ./etc_rc.local:/etc/rc.local
         - ./node2/conf:/etc/redis
         - ./node2/data:/data
       command: redis-server /etc/redis/redis.conf
     node3:
       image: redis:latest
       container_name: node3
       networks:
         redis:
           ipv4_address: 172.19.0.14
       ports:
         - "10003:6379"
         - "20003:16379"
       volumes:
         - ./etc_rc.local:/etc/rc.local
         - ./node3/conf:/etc/redis
         - ./node3/data:/data
       command: redis-server /etc/redis/redis.conf
       depends_on:
         - node0
         - node1
         - node2
     node4:
       image: redis:latest
       container_name: node4
       networks:
         redis:
           ipv4_address: 172.19.0.15
       ports:
         - "10004:6379"
         - "20004:16379"
       volumes:
         - ./etc_rc.local:/etc/rc.local
         - ./node4/conf:/etc/redis
         - ./node4/data:/data
       command: redis-server /etc/redis/redis.conf
       depends_on:
         - node0
         - node1
         - node2
     node5:
       image: redis:latest
       container_name: node5
       networks:
         redis:
           ipv4_address: 172.19.0.16
       ports:
         - "10005:6379"
         - "20005:16379"
       volumes:
         - ./etc_rc.local:/etc/rc.local
         - ./node5/conf:/etc/redis
         - ./node5/data:/data
       command: redis-server /etc/redis/redis.conf
       depends_on:
         - node0
         - node1
         - node2
   
   # 自动创建网络，并手动指定IP网段
   networks:
     redis:
       ipam:
         config:
           - subnet: 172.19.0.0/16
   ```

3. 运行`node.sh`生成若干个节点配置信息，然后运行集群：

   ```sh
   docker compose -f docker-compose.yml -p redis up -d
   ```

4. 根据某个节点映射的端口及conf配置中的密码信息可以连接之。

5. 注意：如果一直没法成功创建集群，最好看下是否设置密码、master密码，以及集群启动命令里是否加了master 密码验证参数。

7. 通过下线、上线某些节点进行测试[^ 5]。

## canal镜像修改（暂时用不到）

1. 准备conf目录下的配置文件

2. 撰写Dockerfile:

   ```dockerfile
   FROM canal/canal-server:latest
   LABEL authors="GreenhandSW"
   
   COPY conf/example/instance.properties home/admin/canal-server/conf/example/.
   COPY conf/canal.properties home/admin/canal-server/conf/.
   ```

3. 构建镜像

   ```sh
   docker build -t my-canal/latest .
   ```



# 参考资料

[^ 1]: [minikube start](https://minikube.sigs.k8s.io/docs/start/)
[^2]: [The Ultimate Guide to Deploying and Managing Redis on Kubernetes](https://www.dragonflydb.io/guides/redis-kubernetes)
[^ 3]: [Docker Compose安装redis-cluster集群 ](https://ihui.ink/post/docker/redis-cluster-docker-compose/)
[^ 4]: [使用 Docker Compose 建立 Redis Cluster ](https://blog.yowko.com/docker-compose-redis-cluster/)
[^ 5]: [docker-compose部署redis cluster集群及常用集群命令](https://blog.csdn.net/lihongbao80/article/details/126176403)
[^ 6]: [Docker Compose 搭建 Redis Cluster 集群环境 ](https://www.cnblogs.com/mrhelloworld/p/docker14.html)
[^ 7]: [Spring AOP aspect used in a separate module](https://stackoverflow.com/questions/7797824/spring-aop-aspect-used-in-a-separate-module)

[^ 8]: [springboot整合jpa启动类报错Not a managed type class](https://www.cnblogs.com/javaxubo/p/16558638.html)
[^ 9]: [No beans of 'RestTemplate' type found](https://blog.csdn.net/tongkongyu/article/details/123817121)
[^ 10]: [docker网络模式](https://zhuanlan.zhihu.com/p/640948037)
[^ 11]: [PositionNotFoundException: can't find start position for example #3243 ](https://github.com/alibaba/canal/issues/3243)
[^ 12]: [Java订阅Binlog的几种方式 ](https://jasonkayzk.github.io/2023/03/26/Java%E8%AE%A2%E9%98%85Binlog%E7%9A%84%E5%87%A0%E7%A7%8D%E6%96%B9%E5%BC%8F/)
[^ 13]: 2015年的issue：[CLUSTER MEET doesn't allow hostname to be used #2410 ](https://github.com/redis/redis/issues/2410)，不过到2023年还没解决，还有人在提：[[NEW] Cluster Meet should support hostnames instead of IPs #12508 ](https://github.com/redis/redis/issues/12508)。

