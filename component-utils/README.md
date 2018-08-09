# yoogun-framework project - utils 帮助类包

## 1 说明
本模块包含了全部的平台必要的帮助类。大部分帮助类方法为静态，可供其他模块调用。

## 2 工程内容说明
#### 2.1 src/main/resources
`ehcache-utils.xml`ehcache配置文件;
`fastdfs-client.properties`fastDFS配置文件;
`initialize-utils.properties`帮助类包的初始化配置文件，包含了短信、邮件功能的基本配置信息。

#### 2.2 BsTreeview\CommonTree
bootstrap-tree相关数据DTO

#### 2.3 DictService
系统字典相关内容，因为DictUtils有数据库操作内容，所以额外设立一个服务层对象获取数据库中的字典数据。

#### 2.4 infrastructure/excel/*
注解式的excel导出工具，用法检索参照Customer业务导出客户信息用法。

#### 2.5 infrastructure/*Utils
各种帮助类，用法查阅各自的注释内容。