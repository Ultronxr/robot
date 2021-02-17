# QQ Robot

## 1. 框架

使用开源框架 [mirai](https://github.com/mamoe/mirai) ，其开发文档见 [mirai开发文档](https://github.com/mamoe/mirai/blob/dev/docs/README.md) 。

这里是对mirai的功能应用。

## 2. 依赖

旧版本使用手动安装jar包进行依赖；

更新 `mirai 2.3.2` 版本时变更为使用maven依赖构建，方法见 [在JVM项目中使用mirai](https://github.com/mamoe/mirai/blob/dev/docs/ConfiguringProjects.md) 。

mirai版本规范见此 [链接](https://github.com/mamoe/mirai/blob/dev/docs/Evolution.md#%E7%89%88%E6%9C%AC%E8%A7%84%E8%8C%83) 。

## 3. `deviceInfo.json`设备信息文件路径问题

有关`deviceInfo.json`设备信息文件说明请查阅 [官方说明](https://github.com/mamoe/mirai/blob/dev/docs/Bots.md#%E8%AE%BE%E5%A4%87%E4%BF%A1%E6%81%AF) 。

在线生成`deviceInfo.json`文件 [链接](https://ryoii.github.io/mirai-devicejs-generator/) 。

初始化QQ机器人的库函数只接收`deviceInfo.json`的字符串路径，在IDE中未打jar包时可以使用如*src\main\resources\deviceInfo.json*的路径，且编译运行正常。

（编译后该文件会被复制到*classpath*路径下）

但maven package打包成jar包再运行会报错提示找不到该文件。


为了解决这个问题，这里采用了如下方式（**是否可以更完善**）：

检测当前是否在jar包中运行，

+ 如果不在jar包中运行，则直接使用*classpath*中的`deviceInfo.json`文件路径。

+ 如果在jar包中运行，则不使用jar包*classpath*中的`deviceInfo.json`文件路径，改成jar包所在的路径（由jar包内路径改成jar包外路径）。

