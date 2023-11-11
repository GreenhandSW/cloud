#!/bin/bash

# 关掉正在运行的容器，清理文件
docker compose down
docker network remove cloud
sudo rm -rf */

# 新建配置文件
# Define the number of nodes you want to create
#num_nodes=3
master_nodes=1
slave_nodes=2

# Create the masters
for i in $(seq 0 $((master_nodes-1))); do
    node_dir="master$i"
    rm -rf $node_dir
    mkdir -p "$node_dir"/{data,conf,log}
    ID=$((i+1)) envsubst < master-cnf.tmpl > $node_dir/conf/my.cnf
done

# Create the slaves
for i in $(seq $((master_nodes)) $((slave_nodes+master_nodes-1))); do
    node_dir="slave$i"
    rm -rf $node_dir
    mkdir -p "$node_dir"/{data,conf,log}
    ID=$((i+1)) envsubst < slave-cnf.tmpl > $node_dir/conf/my.cnf
done

# 启动容器
docker network create cloud
docker compose -f docker-compose.yml -p mysql up -d