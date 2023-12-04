主要记录一些工具的使用方法及注意事项

## JMeter

### 1. 发送POST请求需要设置请求头

GET可以省略，但POST一定要添加一个HTTP信息头管理器，并且添加一个Key=`Content-Type`，Value=`application/json`的值，这样默认的请求格式才会变成json[^1]。



# 参考资料

[^1]: [利用Jmeter 实现Json格式接口测试](https://www.cnblogs.com/luweiwei/p/5320805.html)

