package cn.ultronxr.qqrobot.framework.quartz;

/**
 * @author Ultronxr
 * @date 2022/03/10 19:17
 *
 * QuartzTask 命令枚举
 */
public enum QuartzTaskCmd {

    //HELP("quartz-help", "命令帮助"),
    ADD("quartz-add", "添加任务"),
    PAUSE("quartz-pause", "暂停任务"),
    RESUME("quartz-resume", "恢复任务"),
    STOP("quartz-stop", "停止任务"),
    DELETE("quartz-delete", "删除任务"),
    LIST("quartz-list", "列出所有任务"),
    QUERY("quartz-query", "查看指定的任务消息"),
    COPY("quartz-copy", "复制任务"),
    MODIFY("quartz-modify", "修改任务");


    private final String cmd;
    private final String info;

    QuartzTaskCmd(String cmd, String info) {
        this.cmd = cmd;
        this.info = info;
    }

    public String getCmd() {
        return cmd;
    }

    public String getInfo() {
        return info;
    }

}
