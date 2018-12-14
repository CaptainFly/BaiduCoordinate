# framework_demo

# 开发框架演示系统

源自[JeeSite](https://github.com/thinkgem/jeesite)开源开发框架。

## 快速体验

1. 具备运行环境：JDK1.7+、Maven3.0+、MySql5+
2. 修改src\main\profiles\oa-dev.properties文件中的数据库设置参数
3. 根据修改参数创建对应MySql数据库用户和参数
4. 运行initdb.sql，创建数据库
5. 运行`mvn tomcat7:run`启动Web服务器（第一次运行，需要下载依赖jar包，请耐心等待）
6. 管理员账号，用户名：admin 密码：admin


## 生产环境打包

```
mvn clean package -Pprod
```


