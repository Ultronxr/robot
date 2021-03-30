如果phantomjs在Linux环境下对含有中文的网页截图时出现乱码（同时往往伴有排版错乱的情况），

请安装字体（这里我用到了苹方字体，附压缩包于此，其他字体文件请自行获取）：

+ 把字体文件上传到linux系统/usr/share/fonts/chinese中，如果没有文件夹，创建文件夹

+ 安装字体管理工具

```bash
# dnf install fontconfig mkfontscale
或
# apt install fontconfig
```

+ 安装字体

```bash
# cd /usr/share/fonts/chinese
# mkfontscale
# mkfontdir
# fc-cache -fv
```

+ 查看安装结果

```bash
# fc-list :lang=zh
```
