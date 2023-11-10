# 介绍
个人针对[spring cloud微服务项目](https://www.bilibili.com/video/BV18E411x7eT)的学习。参考了[**小楊同学的笔记本**的学习笔记](https://blog.csdn.net/qq_36903261/category_10087946.html)。
# 使用方法
## 特殊
由于本项目用gitignore排除掉了几乎所有IDE的设置信息，因此需要：
1. 手动添加模块：右键对准每个模块的`pom.xml`文件，点击<kbd>Add as Maven Project</kbd>选项即可。
## 测试
[src/main/resources](src/main/resources)目录下有名为`cloud.http`的测试脚本，包含了对每个API的测试，其详细环境信息写在`http-client.private.env.json`文件内。
# 其他
pay项目下的`./database/redis/deprecated`目录存放k8s搭建redis集群的脚本、配置，未完成，暂时弃用。