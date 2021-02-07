package cn.ultronxr.qqrobot.bean;


public enum MiraiLabel {
    //BOT消息
    //@BOT
    //BOT_AT("@"+ BotEntity.BOT_NICK, "[mirai:at:"+ BotEntity.BOT_QQ+",@"+ BotEntity.BOT_NICK+"]"),
    BOT_AT("@"+ BotEntity.BOT_NICK, "[mirai:at:" + BotEntity.BOT_QQ + "]"),


    //特殊消息
    //腾讯地图定位
    APP_TENCENT_MAP("com.tencent.map", "\"app\":\"com.tencent.map\"");




    private String label;
    private String content;

    MiraiLabel(String label, String content){
        this.label = label;
        this.content = content;
    }

    MiraiLabel(String label, String content, int index) {
        this.label = label;
        this.content = content;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
