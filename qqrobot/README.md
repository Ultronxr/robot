# QQ Robot

当前QQRobot版本：[查看pom.xml文件version标签](./pom.xml#L13)

依赖mirai版本：2.6.4

## 1. 项目主要目录结构

```text
\--qqrobot                             项目根目录
   \--src
      |--main                            主项目文件包
      |  |--java
      |  |  \--cn
      |  |     \--ultronxr
      |  |        \--qqrobot                 QQ机器人代码包
      |  |           |--annotation             自定义注解包
      |  |           |--aspect                 AOP切面定义与advice内容包
      |  |           |--bean                   通用数据对象bean包
      |  |           |  \--mybatis               Mybatis数据库表映射对象包
      |  |           |     |--bean                 数据库表映射bean
      |  |           |     \--mapper               数据库表映射mapper
      |  |           |--config                 项目配置包
      |  |           |--eventHandler           机器人事件处理器接口包
      |  |           |  \--eventHandlerImpl      机器人事件处理器接口实现包
      |  |           |--listener               机器人事件监听器包
      |  |           |--service                具体逻辑处理代码接口包
      |  |           |  \--serviceImpl           具体逻辑处理代码接口实现包
      |  |           \--util                   公共方法类包
      |  \--resources                      项目资源文件包
      |     |--fonts                         字体文件资源
      |     |--img                           图像文件资源
      |     \--mapper                        数据库表映射mapper.xml
      \--test                            项目测试包
```

## 2. 框架

使用开源框架 [mirai](https://github.com/mamoe/mirai) ，其开发文档见 [mirai开发文档](https://github.com/mamoe/mirai/blob/dev/docs/README.md) ，其Java API文档见 [Mirai - Core API](https://github.com/mamoe/mirai/blob/dev/docs/CoreAPI.md) 。

这里是对mirai的功能应用。

## 3. 依赖

旧版本使用手动安装jar包进行依赖；

更新 `mirai 2.3.2` 版本时变更为使用maven依赖构建，方法见 [在JVM项目中使用mirai](https://github.com/mamoe/mirai/blob/dev/docs/ConfiguringProjects.md) 。

mirai版本规范见此 [链接](https://github.com/mamoe/mirai/blob/dev/docs/Evolution.md#%E7%89%88%E6%9C%AC%E8%A7%84%E8%8C%83) 。

## 4. `deviceInfo.json`设备信息文件路径问题

有关`deviceInfo.json`设备信息文件说明请查阅 [官方说明](https://github.com/mamoe/mirai/blob/dev/docs/Bots.md#%E8%AE%BE%E5%A4%87%E4%BF%A1%E6%81%AF) 。

在线生成`deviceInfo.json`文件 [链接](https://ryoii.github.io/mirai-devicejs-generator/) 。

初始化QQ机器人的库函数只接收 `deviceInfo.json` 的字符串路径，在IDE中未打jar包时可以使用如下路径，编译运行正常：

```text
src\main\resources\deviceInfo.json
```

但maven package打包成jar包再运行会报错提示找不到该文件，因为jar包中的文件路径是：

```text
qqrobot.jar!/BOOT-INF/classes!/deviceInfo.json
```

且使用如下代码也无效：

```java
BotEntity.class.getResource("/deviceInfo.json").getPath();
BotEntity.class.getResourceAsStream("/deviceInfo.json");
```


为了解决这个问题，这里采用了如下方式（**是否可以更完善**）：

检测当前是否在jar包中运行，

+ 如果不在jar包中运行，则直接使用*classpath*中的`deviceInfo.json`文件路径。

+ 如果在jar包中运行，则不使用jar包*classpath*中的`deviceInfo.json`文件路径，改成jar包所处路径（由jar包内路径改成jar包外路径），当然 **这需要在jar包所处路径手动放置deviceInfo.json文件** 。

## 5. 事件通道（EventChannel）和事件（Event）监听注册

[事件通道](https://github.com/mamoe/mirai/blob/dev/docs/Events.md#%E4%BA%8B%E4%BB%B6%E9%80%9A%E9%81%93) 介绍

支持的所有 [事件列表](https://github.com/mamoe/mirai/blob/dev/mirai-core-api/src/commonMain/kotlin/event/events/README.md)

+ 机器人事件通道的获取、所有事件的监听（Listener）注册只在 [`cn.ultronxr.qqrobot.listener.AllListenerRegister`](src/main/java/cn/ultronxr/qqrobot/listener/AllListenerRegister.java) 文件内进行；

+ [`cn.ultronxr.qqrobot.listener`](src/main/java/cn/ultronxr/qqrobot/listener) 包内的其他Listener用于预处理内容/逻辑繁复的事件，辅助 `AllListenerRegister` 进行事件注册；

+ [`cn.ultronxr.qqrobot.eventHandler`](src/main/java/cn/ultronxr/qqrobot/eventHandler) 包内包括了所有事件处理器（Handler）接口及其实现方法，被事件监听器调用；

+ 上述 `listener` 和 `eventHandler` 包内文件的命名遵循事件分类易读的原则，以 **Bot** 命名开头的文件中的方法都是与 `BOT事件` 相关的；以 **Msg** 命名开头的文件中的方法都是与 `消息事件` 相关的；以 **Group** 命名开头的文件中的方法都是与 `群事件` 相关的；等等；

## 6. 命令化的消息语句格式

此QQRobot与QQ消息内容的交互采用 `命令化的消息语句格式` 。

使用 命令化的消息语句格式 的消息语句包含如下内容：

1. 命令前导符，即“>”符号，指明这是一条功能命令消息语句。
2. 命令关键词，即命令名称，由此确认这条语句是什么功能命令。
3. 命令选项，使用命令选项会将功能命令导向不同的逻辑处理分支。
4. 命令选项参数，向命令选项输入参数内容。

例如我需要QQRobot发送一个 **包含若干空行的清屏（刷屏）消息** ，那么你可以发送以下消息中的任意一行：

```text
>clear
>clear 25
>clear -l 25
>clear --line 25
>clear --line=25

注：
上述消息中；“>”为命令前导符；“clear”为命令关键词；“-l” “--line”为命令选项；“25” “=25”为命令选项参数。
```

如果我需要让QQRobot发送有关这条命令的帮助信息，那么你可以发送以下消息：

```text
>clear --help

请注意：
使用如下消息无法让你获取正确的命令帮助信息，因为帮助信息只能使用--help命令选项获取，命令选项-h保留给其他用途。
>clear -h
```

为了实现上述功能，在此引入下面一节内容：[BotCmd与BotMenu架构](#7-botcmd-与-botmenu-架构)

## 7. `BotCmd` 与 `BotMenu` 架构

`BotCmd` 是对BOT功能命令的封装，一个BotCmd代表一个业务功能命令，所有BotCmd组合成 `BotMenu` ，即BOT功能菜单，统一管理业务功能。

（下图是白板鼠标画的，很粗糙，后续可能会重绘一下）

![BotCmd与BotMenu架构](./BotCmd与BotMenu架构.svg)

## 8. 项目代码中使用 `BotCmd` 与 `BotMenu` 架构

1. 在 [botMenu.yaml](src/main/resources/botMenu.yaml) 配置文件中配置功能命令和命令选项；
2. 在 [eventHandler包](src/main/java/cn/ultronxr/qqrobot/eventHandler) 添加事件处理器（Handler）代码；
3. 在 [service包](src/main/java/cn/ultronxr/qqrobot/service) 添加业务逻辑处理（Service）代码；
4. 在 [BotMenuConfig.java](src/main/java/cn/ultronxr/qqrobot/config/BotMenuConfig.java) 文件中注入（@Autowired）handler对象。

注：

+ 有关botMenu.yaml配置文件的配置格式，请查看该文件内开头的注释内容，解释了各个配置项的含义和要求；
+ 命令筛查步骤代码请查看 [MsgGroupListener.java](src/main/java/cn/ultronxr/qqrobot/listener/MsgGroupListener.java) ；
+ 命令解析步骤代码请查看 [BotCmdHandler.java](src/main/java/cn/ultronxr/qqrobot/eventHandler/BotCmdHandler.java) ；
+ 涉及到事件监听器和事件处理器的内容，请参见本文前面的 [事件通道（EventChannel）和事件（Event）监听注册](#5-事件通道eventchannel和事件event监听注册) 。
