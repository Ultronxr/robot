# QQ Robot

## 框架

使用开源框架 [mirai](https://github.com/mamoe/mirai) ，其开发文档见 [mirai开发文档](https://github.com/mamoe/mirai/blob/dev/docs/README.md) 。

这里是对mirai的功能应用。

## 依赖

旧版本使用手动安装jar包进行依赖；

更新 `mirai 2.3.2` 版本时变更为使用maven依赖构建，方法见 [在JVM项目中使用mirai](https://github.com/mamoe/mirai/blob/dev/docs/ConfiguringProjects.md) 。

## `deviceInfo.json`设备信息文件路径问题

初始化QQ机器人的库函数只接收`deviceInfo.json`的字符串路径，在IDE中未打jar包时可以使用如*src\main\resources\deviceInfo.json*的路径，且编译运行正常。

（编译后该文件会被复制到*classpath*路径下）

但maven package打包成jar包再运行会报错提示找不到该文件。


为了解决这个问题，这里采用了如下方式（**是否可以更完善**）：

检测当前是否在jar包中运行，

+ 如果不在jar包中运行，则直接使用*classpath*中的`deviceInfo.json`文件路径。

+ 如果在jar包中运行，则不使用jar包*classpath*中的`deviceInfo.json`文件路径，改成jar包所在的路径（由jar包内路径改成jar包外路径）。

    由于修改了日志记录方式，认证新设备的链接URL不会在日志中输出，所以无法产生新的`deviceInfo.json`文件，这需要将事先运行产生的deviceInfo.json文件拷贝到jar包的相同目录下。
    
    或者在部署时不把日志定向到*/dev/null*，认证新设备的链接URL会在nohup日志中输出。

