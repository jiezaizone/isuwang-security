# isuwang-security

##1、运行security-demo，访问demo url http://localhost:8080/hello 提示输入用户名密码，配置application.properties， 添加以下配置关闭spring-security默认行为
```
#关闭spring-security 默认配置
security.basic.enabled=false
```
之后重新访问demo url http://localhost:8080/hello 便可以等到正常返回

#第三方登录
1、qq登录
默认地址：/auth/qq

其中auth 是SocialAuthenticationFilter接口配置的默认值，qq 是QQProperties配置的默认providerId

可通过配置更改filterProcessesUrl 更改默认值"auth"
