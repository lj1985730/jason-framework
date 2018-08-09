# yoogun-framework project 大连用友-客户开发部-开发框架

## 1 工程结构
本框架依靠Maven构建，结构上分若干模块：
#### 1.1 system(系统包)：
`system-import`：依赖管理包，其中按照不同分类管理整个框架的依赖声明。供其他模块引用。
`system-core`：核心包，包含框架核心功能内容。
`system-web`：客户端基础包，包含框架客户端核心功能内容，包括基本客户端框架（bootstrap3、jQuery等等）。
`system-database`：数据库包，包含数据库交互相关内容。
`system-auth`：身份认证与权限包，包含框架整体权限功能。
`system-base`：系统管理包，包含例如字典、系统参数等功能。
#### 1.2 component(组件包)：
`component-chart`：图表组件包，包含以HighChart为基础的图表支持功能。（待实现）
`component-codegen`：代码生成包，封装了MybatisGenerator，能够按照单表生成工程文件。
`component-initialize`：初始化包，包含了平台全部初始化脚本等等内容。
`component-utils`：工具包，包含系统通用的Utils工具，例如字典下拉菜单、文件处理工具等等。此外，系统内置的ehcache缓存在此包中配置。
`component-workflow`：工作流组件包，包含以Activiti为基础的工作流相关封装功能。
`component-report`：报表组件包，包含报表支持功能。（待实现）
`component-communication`：通信组件包，包含基础通信相关功能封装，例如WebService、WebSocket等等。（待实现）
`component-task`：任务组件包，包含任务、事件、消息相关功能封装，包括定时任务、消息队列等等。（待实现）
#### 1.3 module(业务模块包)：
`module-XXX`：具体按照系统或者业务分模块。业务模块包不能单独运行。
#### 1.4 project(工程包)：
`project-XXX`：具体业务包整合成工程包，工程包是最终部署产物。

## 2 打包方式
推荐使用intellij的Maven工具打包。在界面右侧的Maven Projects面板中，
先选择yoogun-framework下的Lifecycle下的clean来清除之前遗留的旧内容，
再选择package进行打包，如需要打crm包，则打好的包位置放在本机yoogun-framework路径下的project-crm/target中。