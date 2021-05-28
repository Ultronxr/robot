package cn.ultronxr.qqrobot.bean;

import net.mamoe.mirai.message.data.At;


/**
 * @author Ultronxr
 * @date 2021/01/25 21:08
 *
 * MiraiCodes，Mirai码，Mirai特殊消息类型标识符
 * 为了与原对象 {@code net.mamoe.mirai.message.code.MiraiCode} 区别这里命名为MiraiCodes
 * 用于标识QQ消息中针对BOT机器人的各种特殊消息类型（例：@BOT、@全体成员...）
 *
 * 获取与鉴别特殊消息类型请使用官方提供的API方法 {@code message.serializeToMiraiCode()} ，
 * 请勿使用 {@code message.contentToString()} 方法与 {@code message.toString()} 方法。
 * 有关上述三个方法的区别详见如下链接：
 * {@link "https://github.com/mamoe/mirai/blob/dev/docs/Messages.md#%E6%B6%88%E6%81%AF%E5%85%83%E7%B4%A0"}
 */
public enum MiraiCodes {
    AT_BOT("[mirai:at:" + BotEntity.BOT_QQ + "]", new At(BotEntity.BOT_QQ_LONG), "@BOT"),

    AT_ALL("[mirai:atall]", null, "@全体成员"),


    //特殊消息
    //腾讯地图定位
    APP_TENCENT_MAP("\"app\":\"com.tencent.map\"", null, "com.tencent.map");


    /** Mirai码 */
    private String code;
    /** 可以用来特征判断的对象 */
    private Object codeObj;
    /** 备注/解释 */
    private String note;

    MiraiCodes(String code, Object codeObj, String note) {
        this.code = code;
        this.codeObj = codeObj;
        this.note = note;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getCodeObj() {
        return codeObj;
    }

    public void setCodeObj(Object codeObj) {
        this.codeObj = codeObj;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
