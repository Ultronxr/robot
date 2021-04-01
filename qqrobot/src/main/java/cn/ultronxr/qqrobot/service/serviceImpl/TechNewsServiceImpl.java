package cn.ultronxr.qqrobot.service.serviceImpl;

import cn.ultronxr.qqrobot.service.TechNewsService;
import cn.ultronxr.qqrobot.util.AliOSSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.File;


@Service
@Slf4j
public class TechNewsServiceImpl implements TechNewsService {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /** redis键-企查查日报信息Map */
    private static final String KEY_QICHACHA_NEWS_MAP = "qichacha_news_map";

    /** 区分早报和晚报的奇偶参数 */
    private static final Integer NEWS_ODD = 1;

    /** 图片保存位置 前导路径 */
    private static final String QICHACHA_NEWS_PATH_PREFIX = "cache" + File.separator + "qichacha_news" + File.separator;

    /** 图片上传到OSS的前导路径KEY */
    private static final String OSS_FOLDER_KEY = "workspace/java/robot/remote/qichacha_news/";


    @Override
    public String getQichachaMorningNewsFilename() {
        Integer actualNumber = (Integer) redisTemplate.opsForHash().get(KEY_QICHACHA_NEWS_MAP, "actualNumber");
        if(null == actualNumber){
            return null;
        }
        // 如果最新的一期是晚报，那么返回上一期早报
        if(actualNumber % 2 != NEWS_ODD){
            actualNumber -= 1;
        }
        String fileKey = actualNumber + ".png";
        String filename = QICHACHA_NEWS_PATH_PREFIX + fileKey;

        if(! new File(filename).exists()){
            if(AliOSSUtils.getFileObject(OSS_FOLDER_KEY, fileKey, filename)){
                log.info("[function] 从OSS获取企查查早报截图 {} 成功。", fileKey);
            } else {
                log.warn("[function] 从OSS获取企查查早报截图 {} 失败！", fileKey);
            }
        }

        log.info("[function] 获取企查查早报文件名，actualNumber - {}，返回文件名 - {}", actualNumber, filename);
        return filename;
    }

    @Override
    public String getQichachaEveningNewsFilename() {
        Integer actualNumber = (Integer) redisTemplate.opsForHash().get(KEY_QICHACHA_NEWS_MAP, "actualNumber");
        if(null == actualNumber){
            return null;
        }
        // 如果最新的一期是早报，那么返回上一期晚报
        if(actualNumber % 2 == NEWS_ODD){
            actualNumber -= 1;
        }
        String fileKey = actualNumber + ".png";
        String filename = QICHACHA_NEWS_PATH_PREFIX + fileKey;

        if(! new File(filename).exists()){
            if(AliOSSUtils.getFileObject(OSS_FOLDER_KEY, fileKey, filename)){
                log.info("[function] 从OSS获取企查查晚报截图 {} 成功。", fileKey);
            } else {
                log.warn("[function] 从OSS获取企查查晚报截图 {} 失败！", fileKey);
            }
        }

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

}
