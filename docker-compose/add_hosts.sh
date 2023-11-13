#!/bin/bash

# 本脚本主要用于
source global.sh

# 读取hosts文件内容到变量中
hosts_content=$(cat "$file")

for ((i=0; i<count; i++)); do
  hostname="$hostname_prefix$i"
  current_mapping_line=$(eval echo "\"${mapping_line}\"")

  if ! grep -qxF -- "${current_mapping_line}" "$file"; then
    sudo echo "${current_mapping_line}" >> "$file"
    echo "已添加 $hostname 的映射。"
  else
    echo "已存在 $hostname 的映射，跳过添加。"
  fi
done

echo "映射添加完成。"
