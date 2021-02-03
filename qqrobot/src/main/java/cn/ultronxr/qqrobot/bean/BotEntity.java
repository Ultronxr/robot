package cn.ultronxr.qqrobot.bean;

import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedHashMap;


@Component
@Slf4j
public class BotEntity extends GlobalData {

    public static final String BOT_QQ = ResBundle.BOT.getString("bot.qq");

    private static final String BOT_PWD = ResBundle.BOT.getString("bot.pwd");

    public static final String BOT_NICK = ResBundle.BOT.getString("bot.nick");

    public static String DEVICE_INFO_FILE_PATH;

    public static final Bot BOT_ENTITY;

    static {
        String classpathFilePath = "src\\main\\resources\\deviceInfo.json";
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
                log.info("[system] deviceInfo.json - 在jar包中运行，选择userDir路径。");
            } else{
                DEVICE_INFO_FILE_PATH = classpathFilePath;
                log.info("[system] deviceInfo.json - 不在jar包中运行，选择classpathFilePath路径。");
            }
            log.info("[system] deviceInfo.json - DEVICE_INFO_FILE_PATH : " + DEVICE_INFO_FILE_PATH);

            BOT_ENTITY = BotFactoryJvm.newBot(
                    Long.parseLong(BOT_QQ), BOT_PWD,
                    new BotConfiguration(){{fileBasedDeviceInfo(DEVICE_INFO_FILE_PATH);}}
            );
        }
    }

    /**
     * 文字版机器人功能菜单，仅供查询功能使用
     * TODO 20210203 后续完善：改成可以直接通这个Map直接调用到Handler，在Listener里面无需列一遍所有功能，而是可以遍历这个Map
     */
    public static final LinkedHashMap<Integer, String[]> MENU_TEXT = new LinkedHashMap<Integer, String[]>(){{
        put(0, new String[]{"功能", "菜单"});
        put(1, new String[]{"ping"});
        put(2, new String[]{"图片"});
        put(3, new String[]{"舔狗", "彩虹屁"});
        put(4, new String[]{"脏话", "口吐芬芳", "芬芳"});
        put(5, new String[]{"火力全开"});
    }};

}
