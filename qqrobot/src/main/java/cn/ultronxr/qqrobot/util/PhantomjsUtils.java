package cn.ultronxr.qqrobot.util;

import cn.ultronxr.qqrobot.bean.GlobalData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;


/**
 * PhantomJS无界面浏览器 工具类
 * {@link "https://phantomjs.org/"}
 *
 * 在系统中配置可执行文件 bin/phantomjs(.exe)：
 *   Windows - 把 phantomjs.exe 所在目录加入环境变量
 *   Linux   - 把 phantomjs 放入 /usr/local/bin 目录
 *
 * qqrobot/src/lib/phantomjs 目录有2.1.1版本源文件，请按需取用
 */
@Slf4j
public class PhantomjsUtils {

    /**
     * rasterize.js 是官方对于 网页截图 的支持库
     * {@link "https://github.com/ariya/phantomjs/blob/master/examples/rasterize.js"}
     *
     * 这里我修改了其中部分内容（另加一个 pageTimeout 参数），所以是 rasterize_my.js
     * 同样地，在 qqrobot/src/lib/phantomjs 目录下有这个文件，请自行决定这里的路径，确保程序能访问到
     */
    private static final String RASTERIZE_JS = "cache" + File.separator + "rasterize_my_2.js";

    /** 可执行文件/命令名称 */
    private static String PHANTOMJS;

    static {
        PHANTOMJS = "phantomjs";
        if(GlobalData.OS_NAME.contains("Windows")) {
            PHANTOMJS += ".exe";
        }
        PHANTOMJS += " --ignore-ssl-errors=true --ssl-protocol=any";
    }


    /**
     * 调用命令行 phantomjs(.exe) rasterize.js 进行网页截图
     * {@link "https://phantomjs.org/screen-capture.html"}
     *
     * 命令格式：
     *   phantomjs.exe rasterize.js "https://baidu.com" "baidu.png" "600*800px" 1.2 5000
     *
     * @param url                   截图目标网页URL
     * @param outputPathAndFilename 截图输出路径和文件名（需要带后缀名）
     * @param width                 网页宽度（单位：像素px）
     * @param height                网页高度（单位：像素px）
     * @param zoom                  网页缩放比例（1 = 100%）
     * @param timeout               等待网页加载时间（单位：毫秒ms）
     */
    public static Boolean screenCapture(String url, String outputPathAndFilename, Integer width, Integer height, Float zoom, Integer timeout)
            throws IOException, InterruptedException {
        if(StringUtils.isEmpty(url)){
            return false;
        }
        if(StringUtils.isEmpty(outputPathAndFilename)){
            return false;
        }
        if(null == width || 0 >= width){
            width = 600;
        }
        if(null == height || 0 >= height){
            height = 800;
        }
        if(null == zoom || 0 >= zoom){
            zoom = 1.0f;
        }
        if(null == timeout || 0 >= timeout){
            timeout = 1000;
        }

        StringBuilder cmd = new StringBuilder();
        cmd.append(PHANTOMJS).append(" ").append(RASTERIZE_JS)
                .append(" \"").append(url).append("\" ")
                .append(" \"").append(outputPathAndFilename).append("\" ")
                .append(" \"").append(width).append("*").append(height).append("px\" ")
                .append(zoom).append(" ")
                .append(timeout);
        log.info("[function] phantomjs截图cmd：{}", cmd);

        Process process = null;
        if(GlobalData.OS_NAME.contains("Windows")){
            process = Runtime.getRuntime().exec(cmd.toString());
        } else {
            process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", cmd.toString()});
        }
        process.waitFor();

        if(0 == process.exitValue() && new File(outputPathAndFilename).exists()){
            log.info("[function] phantomjs网页截图成功：url-{}, outputPathAndFilename-{}, width*height-{}*{}px, zoom-{}, timeout-{}",
                    url, outputPathAndFilename, width, height, zoom, timeout);
            return true;
        }
        log.warn("[function] phantomjs网页截图失败！");
        return false;
    }

}
