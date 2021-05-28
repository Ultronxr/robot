package cn.ultronxr.qqrobot.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author Ultronxr
 * @date 2021/05/07 18:51
 *
 * BOT功能命令菜单
 */
@Data
@NoArgsConstructor
@Component
@Slf4j
public class BotMenu {

    /** 功能命令菜单（即所有BotCmd组成的List） */
    public static ArrayList<BotCmd> BOT_MENU = new ArrayList<>();

    /** 功能命令菜单文字版 */
    private static String BOT_MENU_TEXT = null;


    /**
     * 获取文字版的功能命令菜单
     */
    public static String getMenuText() {
        if(BOT_MENU_TEXT == null) {
            StringBuilder strBuilder = new StringBuilder();
            BOT_MENU.forEach(cmd -> {
                strBuilder.append("○ ").append(cmd.getBriefDesc()).append("\n  ")
                        .append(cmd.getTriggerListString(false).append("\n"));
            });
            BOT_MENU_TEXT = strBuilder.toString();
        }
        return BOT_MENU_TEXT;
    }

    /**
     * 检查一条纯文本消息语句触发了哪条BOT功能命令 <br/>
     * 检查方法为消息语句是否以命令关键词开头<br/>
     * 如果命令关键词设置不当，可能会导致一条消息匹配到多个功能命令，
     * 所以这个方法总是只返回第一个（从下标0开始递增查找）匹配的功能命令
     *
     * @param msgPlain 纯文本消息语句
     * @return {@code BotCmd} 触发的BOT功能命令对象，如果没有匹配结果返回null
     */
    public static BotCmd checkBotCmd(String msgPlain) {
        if(StringUtils.isEmpty(msgPlain)) {
            return null;
        }
        msgPlain = msgPlain.strip();
        // 数据量不大直接嵌套for
        for(BotCmd cmd : BOT_MENU) {
            for(String trigger : cmd.getTriggerList()) {
                if(msgPlain.startsWith(trigger)) {
                    return cmd;
                }
            }
        }
        return null;
    }

}
