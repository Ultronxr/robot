package cn.ultronxr.qqrobot.bean;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.utils.BotConfiguration;

import java.util.LinkedHashMap;

public class BotEntity extends GlobalData {

    public static final String BOT_QQ = ResBundle.BOT.getString("bot.qq");

    private static final String BOT_PWD = ResBundle.BOT.getString("bot.pwd");

    public static final String BOT_NICK = ResBundle.BOT.getString("bot.nick");

    private static final String DEVICE_INFO_FILE_PATH = "qqrobot\\src\\main\\resources\\deviceInfo.json";

    public static final Bot BOT_ENTITY = BotFactoryJvm.newBot(
            Long.parseLong(BOT_QQ), BOT_PWD,
            new BotConfiguration(){{fileBasedDeviceInfo(DEVICE_INFO_FILE_PATH);}}
    );

    /**
     * 文字版机器人功能菜单，仅供查询功能使用
     * TODO 20210203 后续完善：改成可以直接通这个Map直接调用到Handler，在Listener里面无需列一遍所有功能，而是可以遍历这个Map
     */
    public static final LinkedHashMap<Integer, String[]> MENU_TEXT = new LinkedHashMap<Integer, String[]>(){{
        put(0, new String[]{"功能", "菜单"});
        put(1, new String[]{"ping"});
        put(2, new String[]{"图片"});
        put(3, new String[]{"舔狗"});
        put(4, new String[]{"脏话", "口吐芬芳", "芬芳"});
        put(5, new String[]{"火力全开"});
    }};

}
