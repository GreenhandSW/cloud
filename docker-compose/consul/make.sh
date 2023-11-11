#!/bin/bash

docker compose down
sudo rm -rf */

docker compose -f docker-compose.yml -p consul up -d