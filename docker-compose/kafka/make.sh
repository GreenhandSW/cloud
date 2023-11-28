#!/bin/bash

docker compose down

sudo rm -rf kafka*

# Define the number of nodes you want to create
num_nodes=3

for i in $(seq 0 $((num_nodes-1))); do
    node_dir="kafka$i"
    rm -rf "$node_dir"
    mkdir -p "$node_dir"
done


docker compose -f docker-compose.yml -p kafka up -d