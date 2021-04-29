# QQ Robot

当前QQRobot版本：2.7.9

依赖mirai版本：2.6.2

## 1. 框架

使用开源框架 [mirai](https://github.com/mamoe/mirai) ，其开发文档见 [mirai开发文档](https://github.com/mamoe/mirai/blob/dev/docs/README.md) ，其Java API文档见 [Mirai - Core API](https://github.com/mamoe/mirai/blob/dev/docs/CoreAPI.md) 。

这里是对mirai的功能应用。

## 2. 项目主要目录结构

```text
\--qqrobot                                     项目根目录
   \--src
      |--main                                  主项目文件包
      |  |--java
      |  |  \--cn
      |  |     \--ultronxr
      |  |        \--qqrobot                   机器人代码包
      |  |           |--annotation             自定义注解包
      |  |           |--aspect                 AOP切面定义与advice内容包
      |  |           |--bean                   通用数据对象Bean包
      |  |           |--config                 项目配置包
      |  |           |--eventHandler           机器人事件处理器接口包
      |  |           |  \--eventHandlerImpl    机器人事件处理器接口实现包
      |  |           |--listener               机器人事件监听器包
      |  |           |--service                具体逻辑处理代码接口包
      |  |           |  \--serviceImpl         具体逻辑处理代码接口实现包
      |  |           \--util                   公共方法类包
      |  \--resources                          项目资源文件包
      \--test                                  测试内容包
```

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

+ 机器人事件通道的获取、所有事件的监听注册只在 [`cn.ultronxr.qqrobot.listener.AllListenerRegister`](src/main/java/cn/ultronxr/qqrobot/listener/AllListenerRegister.java) 文件内进行；

+ [`cn.ultronxr.qqrobot.listener`](src/main/java/cn/ultronxr/qqrobot/listener) 包内的其他Listener用于预处理内容/逻辑繁复的事件，辅助 `AllListenerRegister` 进行事件注册；

+ [`cn.ultronxr.qqrobot.eventHandler`](src/main/java/cn/ultronxr/qqrobot/eventHandler) 包内包括了所有事件处理器接口及其实现方法，被事件监听器调用；

+ 上述 `listener` 和 `eventHandler` 包内文件的命名遵循事件分类易读的原则，以 **Bot** 命名开头的文件中的方法都是与 `BOT事件` 相关的；以 **Msg** 命名开头的文件中的方法都是与 `消息事件` 相关的；以 **Group** 命名开头的文件中的方法都是与 `群事件` 相关的；等等；

