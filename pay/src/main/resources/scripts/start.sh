#docker run -itd -p 6379:6379 --name cloud-redis1 -v ./db/redis/data:/data -v ./db/redis/conf/redis.conf:/etc/redis/redis.conf -d redis redis-server /etc/redis/redis.conf

cd ./db/mysql
docker build -t cloud-mysql:latest .
cd ../..

docker run -itd -p 3306:3306 --name douyin-mysql -v ./db/mysql/log:/var/log/mysql -v ./db/mysql/data:/var/lib/mysql douyin-mysql
