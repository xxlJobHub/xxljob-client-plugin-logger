xxl-job客户端日志插件

Java日志框架的日志输出整合到xxlJob中

### 插件功能

#### xxlJob桥接日志框架

##### 1.`SLF4J Logger`装饰器

##### 2.`SLF4J Logger`动态代理

##### 3.`logback`的`appender`扩展

##### 4.`log4j`的`appender`扩展

#### xxlJob日后扩展

##### 1.同日志框架日志输出格式

##### 2.随日志框架可动态设置级别

>需要Spring环境

##### 3.xxlJob日志格式高度自定义

### 实现思路

#### 1.装饰器日志输出多目的地

#### 2.动态代理`Logger`输出多目的地

#### 3.日志框架`appender`组件