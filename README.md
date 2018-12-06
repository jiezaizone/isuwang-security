# isuwang-security

##1、运行security-demo，访问demo url http://localhost:8080/hello 提示输入用户名密码，配置application.properties， 添加以下配置关闭spring-security默认行为
```
#关闭spring-security 默认配置
security.basic.enabled=false
```
之后重新访问demo url http://localhost:8080/hello 便可以等到正常返回
