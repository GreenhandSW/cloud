#!/bin/bash

docker compose down

node_dir="kafka"
sudo rm -rf "$node_dir"
mkdir -p "$node_dir"


docker compose -f docker-compose.yml -p kafka-single up -d