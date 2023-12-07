create schema payment collate utf8mb4_unicode_ci;
use payment;

create user 'canal'@'%' identified with mysql_native_password by 'cloud';
# alter user 'canal'@'%' identified with mysql_native_password by 'cloud';
grant select, replication slave, replication client on *.* to 'canal'@'%';
delete from mysql.user where user='';
flush privileges;
