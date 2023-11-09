#!/bin/bash

docker compose down
sudo rm -rf zk*
docker compose -f docker-compose.yml -p zookeeper up -d