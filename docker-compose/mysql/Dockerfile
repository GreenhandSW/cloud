FROM mysql:latest
MAINTAINER GreenhandSW

# 数据卷
VOLUME /var/lib/mysql
VOLUME /var/log/mysql

ENV MYSQL_ROOT_PASSWORD cloud
ADD payment.sql /docker-entrypoint-initdb.d
EXPOSE 3306