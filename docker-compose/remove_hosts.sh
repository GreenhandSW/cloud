#!/bin/bash

source global.sh

# 读取hosts文件内容到变量中
hosts_content=$(cat "$file")

for ((i=0; i<count; i++)); do
  hostname="$hostname_prefix$i"
  current_mapping_line=$(eval echo "\"${mapping_line}\"")

  # 使用sed命令删除对应的行
  sudo sed -i "/^${current_mapping_line}$/d" "$file"

  echo "已删除 $hostname 的映射。"
done

echo "映射删除完成。"
