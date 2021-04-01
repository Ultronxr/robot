package cn.ultronxr.qqrobot.service;

import java.io.File;


/**
 * 科技日报 Service
 *
 * 企查查早报/晚报：
 *   这里只保留了 从OSS获取企查查日报截图文件 的代码，
 *   详细截图过程和逻辑（包括注解）被迁移至remote模块，
 *   请参看 {@link cn.ultronxr.remote.phantomjs.technews.service.TechNewsService}
 */
public interface TechNewsService {

    /**
     * 获取最新的企查查早报 文件名
     */
    String getQichachaMorningNewsFilename();

    /**
     * 获取最新的企查查晚报 文件名
     */
    String getQichachaEveningNewsFilename();

    /**
     * 获取最新的企查查早报 文件
     */
    File getQichachaMorningNewsFile();

    /**
     * 获取最新的企查查晚报 文件
     */
    File getQichachaEveningNewsFile();

}
