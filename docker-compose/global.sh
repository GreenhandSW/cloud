#!/bin/bash

# 本脚本主要用于记录需要用到的变量，并备份hosts文件避免出现异常
count=${1:-3}

if [[ $# -eq 0 ]]; then
  count=3  # 将参数默认值设置为3
  echo "没有提供参数，采用默认参数count=$count。"
elif [[ ! $count =~ ^[1-9][0-9]*$ ]]; then
  count=3  # 将参数默认值设置为3
  echo "输入无效，采用默认参数count=$count。"
else
  echo "您输入的注册中心数量为$count"
fi

hostname_prefix="register"
mapping_line="127.0.0.1 \$hostname # 添加 \$hostname 的映射"
file=/etc/hosts

# backup hosts
sudo cp $file ${file}.bak