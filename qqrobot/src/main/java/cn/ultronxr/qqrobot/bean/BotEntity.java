package cn.ultronxr.qqrobot.bean;

import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;


@Component
@Slf4j
public class BotEntity extends GlobalData {

    public static final String BOT_QQ = ResBundle.BOT.getString("bot.qq");

    public static final Long BOT_QQ_LONG = Long.parseLong(BOT_QQ);

    private static final String BOT_PWD = ResBundle.BOT.getString("bot.pwd");

    public static final String BOT_NICK = ResBundle.BOT.getString("bot.nick");

    public static final Bot BOT_ENTITY;

    private static final String DEVICE_INFO_FILE_PATH;

    public static final String LOG_PATH_BOT = ResBundle.BOT.getString("bot.log.path.bot");

    public static final String LOG_PATH_NETWORK = ResBundle.BOT.getString("bot.log.path.network");


    static {
        String classpathFilePath = "src\\main\\resources\\deviceInfo.json";
        //DEVICE_INFO_FILE_PATH = BotEntity.class.getResourceAsStream("/deviceInfo.json");
        try {
            //获取deviceInfo.json文件所在绝对路径
            classpathFilePath = new ClassPathResource("deviceInfo.json").getURI().toString().replace("file:/", "");
        } catch (IOException ex) {
            ex.printStackTrace();
            log.error("[system] 无法获取classpath:deviceInfo.json文件，请检查路径是否正确！");
        } finally {
            //如果当前打成jar包使用java -jar命令运行，则把deviceInfo.json路径修改成jar包所在目录
            //否则程序无法进入jar包获取deviceInfo.json路径，会报错
            if(classpathFilePath.contains(".jar")){
                DEVICE_INFO_FILE_PATH = System.getProperty("user.dir") + "/deviceInfo.json";
                log.info("[system] deviceInfo.json - 在jar包中运行，使用userDir路径。");
            } else{
                DEVICE_INFO_FILE_PATH = classpathFilePath;
                log.info("[system] deviceInfo.json - 不在jar包中运行，使用classpathFilePath路径。");
            }
            log.info("[system] deviceInfo.json - DEVICE_INFO_FILE_PATH : " + DEVICE_INFO_FILE_PATH);

            // 实例化机器人BOT
            // 1.指定设备配置文件路径
            // 2.指定登录协议Android手机端（支持所有功能。注：请勿在另外的安卓手机端再登录，否则会挤掉）
            // 3.设置被挤下线不自动尝试重新登录
            // 4/5.把BOT和NETWORK级别的日志全部重定向到指定目录
            BOT_ENTITY = BotFactory.INSTANCE.newBot(
                    Long.parseLong(BOT_QQ), BOT_PWD,
                    new BotConfiguration(){{
                        fileBasedDeviceInfo(DEVICE_INFO_FILE_PATH);
                        setProtocol(MiraiProtocol.ANDROID_PHONE);
                        setAutoReconnectOnForceOffline(false);
                        redirectBotLogToDirectory(new File(LOG_PATH_BOT));
                        redirectNetworkLogToDirectory(new File(LOG_PATH_NETWORK));
                    }}
            );
        }
    }

    /**
     * 文字版机器人功能菜单，仅供查询功能使用
     * TODO 20210203 后续完善：改成可以直接通这个Map直接调用到Handler，在Listener里面无需列一遍所有功能，而是可以遍历这个Map
     * TODO 2021-4-29 这里的Integer位“编号”暂时不再使用，随便填即可
     */
    public static final LinkedHashMap<Integer, String[]> MENU_TEXT = new LinkedHashMap<Integer, String[]>(){{
        put(0, new String[]{"功能", "菜单"});
        put(9, new String[]{"群成员入群、退群监控"});
        put(1, new String[]{"ping"});
        put(2, new String[]{"天气"});
        //put(3, new String[]{"定时", "定时格式", "定时说明"});
        put(4, new String[]{"phub"});
        put(5, new String[]{"舔狗", "彩虹屁"});
        put(6, new String[]{"脏话", "口吐芬芳", "芬芳"});
        put(7, new String[]{"火力全开"});
        put(8, new String[]{"随机数", "random"});
        put(10, new String[]{"磁力", "种子", "车牌", "magnet"});
    }};

}
