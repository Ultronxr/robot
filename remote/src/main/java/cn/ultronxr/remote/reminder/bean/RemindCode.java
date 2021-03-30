package cn.ultronxr.remote.reminder.bean;

/**
 * 标记是否需要提醒的标识{@code enum}
 */
public enum RemindCode {
    //新闻内容返回不正确
    BadResponse(-1, "-1", "爬取到的新闻内容返回不正确"),

    //新闻内容正确但不包含居住地区，或消息不具时效性
    DoNotRemind(0, "0", "新闻内容正确但不包含居住地区，或消息不具时效性"),

    //新闻内容正确且包含居住地区，且消息具备时效性
    DoRemind(1, "1", "新闻内容正确且包含居住地区，且消息具备时效性");

    private final int intCode;
    private final String strCode;
    private final String msg;

    RemindCode(int intCode, String strCode, String msg) {
        this.intCode = intCode;
        this.strCode= strCode;
        this.msg = msg;
    }

    public int getIntCode() {
        return intCode;
    }

    public String getStrCode() {
        return strCode;
    }

    public String getMsg() {
        return msg;
    }
}
