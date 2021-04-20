# robot总览

每个模块下有单独的功能介绍文档README.md。

## 1. 项目开发与构建环境

Java 11, [openjdk 11](https://adoptopenjdk.net/releases.html?variant=openjdk11&jvmVariant=hotspot)

[maven 3.5.4](https://archive.apache.org/dist/maven/maven-3/3.5.4/binaries/)

IntelliJ IDEA 2020.1.1

## 2. 项目结构

```text
\--robot
   |--common
   |--qqrobot
   \--remote
```

1. `common` 模块：存放公用功能方法（目前暂时无用）；

2. `qqrobot` 模块：主要QQ机器人模块，包含与QQ机器人相关的事件监听、功能实现；

3. `remote` 模块：远程服务器功能模块，由于QQ登录存在异地检测机制，所以我把机器人跑在本地微型服务器上，但是本地机器稳定性不够，部分需要持续稳定运行的独立功能就写在这个模块，放在阿里云服务器上跑。

## 3. 项目版本迭代规范

version:`x.y.z`

1. `x` : 项目架构、框架修改，不提供旧版本兼容支持；

2. `y` : 增减大功能或模块、增减依赖，保证与旧版本的兼容；

3. `z` : 少量代码修改、BUG修复、文档编辑等。
