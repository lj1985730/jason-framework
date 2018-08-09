# yoogun-framework project - codegen 代码生成器

## 1 说明
本生成器通过封装Mybatis-Generator实现功能。
本生成器暂时只能生成单表的工程文件，生成的内容有：
Entity、Dao、Vo、Service、Controller、Jsp、Js
将生成的文件复制到工程后，可直接实现单表数据的CURD操作。
但是大多数情况需要根据具体业需求，対生成的文件进行调整。

## 2 操作步骤
### 2.1 获取最新的版本：
本生成器持续更新，请在使用前更新到最新的版本。
如引用的是jar，请通过maven从私服中获取最新的jar文件。
如直接引用的源码，请从版本控制器中获取最新的源码。之后使用maven执行install命令进行安装。
注意：本工具调用的是编译打包后的jar文件，只修改本包内的源码而不编译打包，修改的内容不会生效。

### 2.2 编辑配置文件：
编辑文件：`src/main/resources/mybatis.properties`
首先确定数据库配置项`jdbc.url`、`jdbc.username`、`jdbc.password`内容无误。

#####codegen.distPath(生成路径)：
设置所要生成的源码所在的绝对路径。
生成的每个不同的源码会按照一定规则，在distPath绝对路径下创建各自的路径。

#####codegen.treeEntity(是否为树形结构):
通常设置成`false`。此时此时生成的Entity会继承`BaseEntity`、生成的Service会继承`BaseAuthService`
当实体类需要继承TreeEntity时，设置为`true`，此时生成的Entity会继承`TreeEntity`、生成的Service会继承`BaseAuthTreeService`

#####codegen.moduleName(模块名称)：
设置所要生成的源码所在的模块的简写名称。例如system-auth模块简写为auth。
注意：模块的简写名称会当做该模块内的第三级包名。例如system-auth下的源码包名为com.yoogun.`auth`

#####codegen.modelName(模型名称)：
设置成期望生成Entity的类名。
注意：生成器生成的一套Dao、Vo、Service、Controller、Jsp、Js的命名均是以Entity的类名为基础形成的。

#####codegen.tableName(表名):
需要配置为数据库表的名称。

#####codegen.developer(开发人名称):
需要配置为开发人员的英文名，用于生成注释。

### 2.3 执行生成：
在maven环境下执行：
```
mybatis-generator:generate
```
命令。之后到指定路径下查看生成的源码。

### 2.4 粘贴源码：
将生成的源码包粘贴到工程里，因为相应的路径会被同时生成，因此可在较根的路径进行粘贴。
注意：上次生成的遗留内容需要手动处理。