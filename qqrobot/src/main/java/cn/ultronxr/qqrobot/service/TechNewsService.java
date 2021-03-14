package cn.ultronxr.qqrobot.service;

import java.io.File;
import java.util.Calendar;
import cn.ultronxr.qqrobot.service.serviceImpl.TechNewsServiceImpl;
import cn.ultronxr.qqrobot.util.DateTimeUtils;


/**
 * 科技日报 Service
 *
 * 以下为 企查查早报/晚报 逻辑：
 *
 *   企查查日报一天两更（周六周日不更），上午9点发布早报，下午5点发布晚报；
 *   定时对早报/晚报截图，并存放在 cache/qichacha_news/ 路径下，以 “期号.png” 格式命名（如“818.png”）；
 *   如果重复获取当天的早报/晚报，就会把缓存中的图片直接发送；否则就先网页截图再发送；
 *
 *   在redis中以 qichacha_news_map 为键维护一个企查查日报信息Map，Map内容请查看：
 *      {@link TechNewsServiceImpl#KEY_QICHACHA_NEWS_MAP}
 *
 *   发送时检查当前时间处于哪个时间段，发送对应的内容：
 *      {@link DateTimeUtils#checkTimeHourPeriod(Calendar, int[])}
 *      这里的时间段为：
 *          0:00 - 9:00 - 17:00 - 24:00
 *               0      1       2
 *       ------------------------------------
 *        时间段 | 早报发送内容 | 晚报发送内容
 *       ------------------------------------
 *           0     昨天的早报    昨天的晚报
 *           1     今天的早报    昨天的晚报
 *           2     今天的早报    今天的晚报
 *       ------------------------------------
 *       周末发送周五的早报和晚报
 */
public interface TechNewsService {

    /**
     * 维护企查查早报/晚报文件 并 更新Redis中的企查查日报信息Map
     */
    void maintainQichachaNewsFileAndRedisMap();

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
