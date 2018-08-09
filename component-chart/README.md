# yoogun-framework project - chart 图表组件包

## 1 说明
本组件包用于封装图表相关功能，采用富服务端的思想。
核心原则是通过构造选型的图表工具模型，来将原始数据，直接转换为客户端图表组件需要的数据格式，减少前台的代码量。
本框架目前使用了echarts作为图表工具。

#### demo
目前DemoController以及WEB-INF/pages/chart/demo下的全部内容为静态的演示例子，确定无用后可删除。

## 2 工程内容说明
src/com/yoogun/chart/application/dto/EchartDto为图表数据传输对象，理论上本对象转换的json可直接被echarts使用。
src/com/yoogun/chart/infrastructure.echarts下均为模拟echart格式的值对象。用于构造EchartsDto结构。

## 3 后续待完善
继续根据需求设计结构性的值对象，有可能需要根据情况，编写对象的构造或者转换方法。
可以根据常用的图形显示模式，预设一些值对象或者DTO，方便快速开发标准模式的图表。