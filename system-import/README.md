# yoogun-framework project - import 依赖管理包

## 1 说明
纯maven结构包，无源码，其中通过pom.xml配置平台以来的资源以及版本。
平台其他包直接依赖资源时，可不声明版本。

#### 1.1 import-commons
声明了apache commons系列的jar依赖。

#### 1.2 import-db
声明了数据库或者缓存相关的jar依赖。

#### 1.3 import-json
声明了JSON相关的jar依赖。

#### 1.4 import-log
声明了日志相关的jar依赖。

#### 1.5 import-orm
声明了ORM相关的jar依赖，目前是mybatis。

#### 1.6 import-others
声明了不属于其他import-XX包的相关的jar依赖，目前是mybatis。

#### 1.7 import-security
声明了权限相关的jar依赖，目前是Shiro。

#### 1.8 import-spring
声明了全部Spring相关的jar依赖。

#### 1.9 import-webservice
声明了WS相关的jar依赖,目前是apache cxf。
