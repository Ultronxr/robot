package cn.ultronxr.qqrobot.service.serviceImpl;

import cn.hutool.core.date.CalendarUtil;
import cn.ultronxr.qqrobot.service.TechNewsService;
import cn.ultronxr.qqrobot.util.DateTimeUtils;
import cn.ultronxr.qqrobot.util.PhantomjsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
@EnableScheduling
public class TechNewsServiceImpl implements TechNewsService {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /** 日期格式化格式 */
    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";

    /**
     * redis键-企查查日报信息Map
     *
     * Map中包含：standardNumber（标准期号）、standardDate（标准日期）、
     *            actualNumber（实际已经获取截图的最新一期期号）
     *
     * 上面的两个（工作日）标准值是手动获取和设置的，用这两个标准值可以计算出任意一天的早报/晚报期号；
     * 这里用于计算 当天最新期号latestNumber，并与 actualNumber 比较：
     *     latestNumber = actualNumber + 2*(当前日期-与standardDate相差工作日天数)
     *     {@link TechNewsServiceImpl#calLatestNumber(Calendar)}
     *
     * 期号奇偶数与早报或晚报取决于下面的NEWS_ODD参数（当天晚报期号=当天早报期号+1）
     */
    private static final String KEY_QICHACHA_NEWS_MAP = "qichacha_news_map";

    private static final Integer STANDARD_NUMBER = 843;

    private static final String STANDARD_DATE = "2021-03-29";

    private static final Calendar STANDARD_DATE_CALENDAR = CalendarUtil.parseByPatterns(STANDARD_DATE, DATE_FORMAT_PATTERN);

    /**
     * 区分早报和晚报的奇偶参数
     * （一开始只有偶数早报、奇数晚报的情况，但是企查查那边不知什么原因有一期是空白的，所以奇偶调换）
     * （为了防止这种情况再发生，这里加一个奇偶参数）
     * 期数groupId % 2 = NEWS_ODD
     * NEWS_ODD = 0 偶数早报、奇数晚报
     * NEWS_ODD = 1 奇数早报、偶数晚报
     */
    private static final Integer NEWS_ODD = 1;


    /** 企查查早报URL（groupId参数为期号） */
    private static final String QICHACHA_MORNING_NEWS_URL = "https://apph5.qichacha.com/h5/app/morning-paper/paper-list?groupId=";

    /** 企查查晚报URL（groupId参数为期号） */
    private static final String QICHACHA_EVENING_NEWS_URL = "https://apph5.qichacha.com/h5/app/evening-paper/list?groupId=";

    /** 图片保存位置 前导路径 */
    private static final String QICHACHA_NEWS_PATH_PREFIX = "cache" + File.separator + "qichacha_news" + File.separator;


    // 每天 9:01:05 和 17:01:05 自动执行
    @Scheduled(cron = "5 1,2 9,17 * * ? ")
    @Override
    public void maintainQichachaNewsFileAndRedisMap() {
        Calendar calendarNow = Calendar.getInstance();
        int[] hours = new int[]{9, 17};

        // 周末不发布日报，直接跳过
        if(DateTimeUtils.isWeekend(calendarNow)){
            return;
        }

        Boolean hasKeyNewsMap = redisTemplate.hasKey(KEY_QICHACHA_NEWS_MAP);
        hasKeyNewsMap = (hasKeyNewsMap == null ? false : hasKeyNewsMap);

        // 1.企查查日报信息Map存在，则进行维护
        if(hasKeyNewsMap) {
            if(0 == DateTimeUtils.checkTimeHourPeriod(calendarNow, hours)){
                // 1.1.早报发布前
                return;
            } else {
                // 1.2.早报发布后（计算当天最新期号并与redis中的实际期号比较，判断是否已经获取截图过当前日报）

                // 计算当天最新期号
                int latestNumber = calLatestNumber(calendarNow);
                // 获取redis中的实际期号
                Integer actualNumber = (Integer) redisTemplate.opsForHash().get(KEY_QICHACHA_NEWS_MAP, "actualNumber");
                actualNumber = (actualNumber == null ? STANDARD_NUMBER : actualNumber);
                log.info("[function] 最新期号latestNumber={} , 实际期号actualNumber={}", latestNumber, actualNumber);

                if(1 == DateTimeUtils.checkTimeHourPeriod(calendarNow, hours)){
                    // 1.2.1.晚报发布前（判断早报期号，若未存在，则进行早报截图）

                    String outputPathAndFilename = QICHACHA_NEWS_PATH_PREFIX + latestNumber + ".png";
                    if(actualNumber == latestNumber && new File(outputPathAndFilename).exists()){
                        log.info("[function] 企查查早报截图已存在，无需重复截图。");
                        return;
                    }
                    try {
                        if(PhantomjsUtils.screenCapture(QICHACHA_MORNING_NEWS_URL+latestNumber, outputPathAndFilename, 650, 6000, 1.3f, 5000)){
                            log.info("[function] 企查查早报网页截图完成。");
                            redisTemplate.opsForHash().put(KEY_QICHACHA_NEWS_MAP, "actualNumber", latestNumber);
                            log.info("[function] redis企查查日报信息MapKey 'actualNumber'-{} 更新完成。", latestNumber);
                        }
                    } catch (IOException | InterruptedException ex){
                        log.warn("[function] 企查查早报网页截图抛出异常！");
                        ex.printStackTrace();
                    }
                } else {
                    // 1.2.2.晚报发布后（判断晚报期号，若未存在，则进行晚报截图）

                    String outputPathAndFilename = QICHACHA_NEWS_PATH_PREFIX+(latestNumber+1) + ".png";
                    if(actualNumber == latestNumber+1 && new File(outputPathAndFilename).exists()){
                        log.info("[function] 企查查晚报截图已存在，无需重复截图。");
                        return;
                    }
                    try {
                        if(PhantomjsUtils.screenCapture(QICHACHA_EVENING_NEWS_URL+(latestNumber+1), outputPathAndFilename, 650, 6000, 1.3f, 5000)){
                            log.info("[function] 企查查晚报网页截图完成。");
                            redisTemplate.opsForHash().put(KEY_QICHACHA_NEWS_MAP, "actualNumber", latestNumber+1);
                            log.info("[function] redis企查查日报信息MapKey 'actualNumber'-{} 更新完成。", latestNumber+1);
                        }
                    } catch (IOException | InterruptedException ex){
                        log.warn("[function] 企查查晚报网页截图抛出异常！");
                        ex.printStackTrace();
                    }
                }
            }
            return;
        }

        // 2.企查查日报信息Map不存在则进行新增，并截取昨天、今天两天的日报截图作为初始数据
        initQichachaNewsFileAndRedisMap();
    }

    private void initQichachaNewsFileAndRedisMap(){
        Calendar calendarNow = Calendar.getInstance();
        int[] hours = new int[]{9, 17};

        int latestNumber = calLatestNumber(calendarNow);
        try {
            if(DateTimeUtils.checkTimeHourPeriod(calendarNow, hours) == 2){
                PhantomjsUtils.screenCapture(QICHACHA_EVENING_NEWS_URL+(latestNumber+1), QICHACHA_NEWS_PATH_PREFIX+(latestNumber+1) + ".png", 650, 6000, 1.3f, 5000);
            }
            if(DateTimeUtils.checkTimeHourPeriod(calendarNow, hours) >= 1){
                PhantomjsUtils.screenCapture(QICHACHA_MORNING_NEWS_URL+(latestNumber), QICHACHA_NEWS_PATH_PREFIX+(latestNumber) + ".png", 650, 6000, 1.3f, 5000);
            }
            PhantomjsUtils.screenCapture(QICHACHA_EVENING_NEWS_URL+(latestNumber-1), QICHACHA_NEWS_PATH_PREFIX+(latestNumber-1) + ".png", 650, 6000, 1.3f, 5000);
            PhantomjsUtils.screenCapture(QICHACHA_MORNING_NEWS_URL+(latestNumber-2), QICHACHA_NEWS_PATH_PREFIX+(latestNumber-2) + ".png", 650, 6000, 1.3f, 5000);
        } catch (IOException | InterruptedException ex){
            log.warn("[function] 初始化企查查日报截图数据抛出异常！");
            ex.printStackTrace();
        }
        log.info("[function] 初始化企查查日报截图数据完成。");

        Map<Object, Object> newsMap = new HashMap<>();
        newsMap.put("standardNumber", STANDARD_NUMBER);
        newsMap.put("standardDate", STANDARD_DATE);
        newsMap.put("actualNumber", latestNumber);
        redisTemplate.opsForHash().putAll(KEY_QICHACHA_NEWS_MAP, newsMap);
        log.info("[function] 向redis新增 企查查日报信息Map {} - {}", KEY_QICHACHA_NEWS_MAP, newsMap);
    }

    @Override
    public String getQichachaMorningNewsFilename() {
        maintainQichachaNewsFileAndRedisMap();
        Integer actualNumber = (Integer) redisTemplate.opsForHash().get(KEY_QICHACHA_NEWS_MAP, "actualNumber");
        if(null == actualNumber){
            return null;
        }
        // 如果最新的一期是晚报，那么返回上一期早报
        if(actualNumber % 2 != NEWS_ODD){
            actualNumber -= 1;
        }
        String filename = QICHACHA_NEWS_PATH_PREFIX + actualNumber + ".png";
        log.info("[function] 获取企查查早报文件名，actualNumber - {}，返回文件名 - {}", actualNumber, filename);
        return filename;
    }

    @Override
    public String getQichachaEveningNewsFilename() {
        maintainQichachaNewsFileAndRedisMap();
        Integer actualNumber = (Integer) redisTemplate.opsForHash().get(KEY_QICHACHA_NEWS_MAP, "actualNumber");
        if(null == actualNumber){
            return null;
        }
        // 如果最新的一期是早报，那么返回上一期晚报
        if(actualNumber % 2 == NEWS_ODD){
            actualNumber -= 1;
        }
        String filename = QICHACHA_NEWS_PATH_PREFIX + actualNumber + ".png";
        log.info("[function] 获取企查查晚报文件名，actualNumber - {}，返回文件名 - {}", actualNumber, filename);
        return filename;
    }

    @Override
    public File getQichachaMorningNewsFile() {
        log.info("[function] 获取企查查早报文件。");
        return new File(getQichachaMorningNewsFilename());
    }

    @Override
    public File getQichachaEveningNewsFile() {
        log.info("[function] 获取企查查晚报文件。");
        return new File(getQichachaEveningNewsFilename());
    }

    /**
     * 计算某一天的早报期号
     *
     * @param calendar 某一天日期，这个日期必须晚于标准日期
     * @return         返回这一天的早报期号；如果传入日期不合法，则返回-1
     */
    private static Integer calLatestNumber(Calendar calendar){
        if(calendar.before(STANDARD_DATE_CALENDAR)){
            return -1;
        }
        return STANDARD_NUMBER + 2 * DateTimeUtils.weekdaysBetweenCalendars(STANDARD_DATE_CALENDAR, calendar);
    }


    /**
     * 手动截图main方法，便于测试功能
     */
    public static void main(String[] args) {
        //int latestNumber = calLatestNumber(Calendar.getInstance());
        int latestNumber = 845;
        String outputPathAndFilename = QICHACHA_NEWS_PATH_PREFIX + latestNumber + ".png";
        String url;
        if(latestNumber % 2 == NEWS_ODD){
            url = QICHACHA_MORNING_NEWS_URL + latestNumber;
        } else {
            url = QICHACHA_EVENING_NEWS_URL + latestNumber;
        }
        try {
            PhantomjsUtils.screenCapture(url, outputPathAndFilename, 650, 6000, 1.3f, 5000);
        } catch (IOException | InterruptedException ex){
            ex.printStackTrace();
        }
    }

}
