#!/bin/bash

# 清除容器和文件夹
docker compose down
echo -e "\
\033[31m***警告***\033[0m
即将删除\033[31m$(pwd)\033[0m目录下自动生成的所有节点的文件夹。
目前的文件夹只有etc_rc.local和redis-node*两种，都是生成的，因此可以直接用rm -rf */删除
如果引入其他配置文件夹，请务必修改这里的删除语句\
"
sudo rm -rf */

# Define the number of nodes you want to create
num_nodes=6

# Create the nodes
# redis不支持以主机名作为地址，因此只能固定Ip。
# 在启动集群时在docker-compose.yml里应该设置一个子网，比如172.200.0.0/16，
# 其中网络第二段应该设置大一点，比如200,避免和其他子网重叠。
# 那么这里的ANNOUNCE_IP就是172.200.0.1。
# 项目中还有很多其他容器，但都可以采用主机名作为地址，因此都划分到另一个子网cloud里了。
for i in $(seq 0 $((num_nodes-1))); do
    node_dir="redis-node$i"
    rm -rf "$node_dir"
    mkdir -p "$node_dir"/{data,conf}
    # redis-node.tmpl是模板文件，用于把同一行的这些变量都填入进去再输出到一个文件里。
    PORT=$((15000 + i)) BUS_PORT=$((PORT + 10000)) ANNOUNCE_IP="172.200.0.1" envsubst < redis-node.tmpl > "$node_dir"/conf/redis.conf
#    touch "$node_dir"/conf/my.conf
done

# 启动容器
docker compose -f docker-compose.yml -p redis up -d