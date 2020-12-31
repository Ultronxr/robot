package cn.ultronxr.qqrobot.data;


public enum MiraiLabel {
    //BOT消息
    //@BOT
    BOT_AT("@"+GlobalData.BOT_NICK, "[mirai:at:"+GlobalData.BOT_QQ+",@"+GlobalData.BOT_NICK+"]"),


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
