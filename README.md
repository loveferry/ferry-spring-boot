# ferry
&emsp;&emsp;Spring Boot 的多模块项目。目前共有基础功能模块，文档模块，soap接口模块。统一调度模块。

## 基础功能模块

- 持久层

&emsp;&emsp;使用mybatis作为持久层框架，二级缓存使用redis

- 通用 mapper

&emsp;&emsp;在tk的通用mapper上进行简化。

- 数据库初始化

&emsp;&emsp;使用liquibase工具进行数据库初始化工作，支持mysql和oracle等数据库。

- 接口功能

&emsp;&emsp;使用swagger作为rest接口的api工具，通过cxf发布web service接口，接口信息查看地址：[http://106.13.78.137:50318/ws](http://106.13.78.137:50318/ws)

## 文档模块

&emsp;&emsp;使用docx4j进行书签替换，支持文本替换，表格替换，图片替换。

## soap接口模块

&emsp;&emsp;soap接口的业务模块，接口的定义，逻辑实现在此模块完成
