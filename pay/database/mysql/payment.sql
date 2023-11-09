create schema payment collate utf8mb4_unicode_ci;
use payment;
CREATE TABLE `payment`(
                          `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                          `serial` VARCHAR(200) DEFAULT '',
                          PRIMARY KEY(`id`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

# select User, Host from mysql.user;
# update mysql.user set Host='%' where User='root';

# flush privileges;

create user 'canal'@'%' identified with mysql_native_password by 'cloud';
# alter user 'canal'@'%' identified with mysql_native_password by 'cloud';
grant select, replication slave, replication client on *.* to 'canal'@'%';
delete from mysql.user where user='';
flush privileges;