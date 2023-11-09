#!/bin/bash

docker compose down
sudo rm -rf */

# Define the number of nodes you want to create
num_nodes=6

# Create the nodes
for i in $(seq 0 $((num_nodes-1))); do
    node_dir="redis-node$i"
    rm -rf $node_dir
    mkdir -p "$node_dir"/{data,conf}
    PORT=$((10000 + ${i})) BUS_PORT=$((PORT + 10000)) ANNOUNCE_IP="172.200.0.1" envsubst < redis-node.tmpl > $node_dir/conf/redis.conf
#    touch "$node_dir"/conf/my.conf
done

docker compose -f docker-compose.yml -p redis up -d