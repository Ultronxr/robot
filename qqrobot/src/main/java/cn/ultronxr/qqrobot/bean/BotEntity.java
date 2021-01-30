package cn.ultronxr.qqrobot.bean;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.utils.BotConfiguration;

public class BotEntity extends GlobalData {

    public static final String BOT_QQ = ResBundle.BOT.getString("bot.qq");

    private static final String BOT_PWD = ResBundle.BOT.getString("bot.pwd");

    public static final String BOT_NICK = ResBundle.BOT.getString("bot.nick");

    private static final String DEVICE_INFO_FILE_PATH = "qqrobot\\src\\main\\resources\\deviceInfo.json";

    public static final Bot BOT_ENTITY = BotFactoryJvm.newBot(
            Long.parseLong(BOT_QQ), BOT_PWD,
            new BotConfiguration(){{fileBasedDeviceInfo(DEVICE_INFO_FILE_PATH);}}
    );

}
