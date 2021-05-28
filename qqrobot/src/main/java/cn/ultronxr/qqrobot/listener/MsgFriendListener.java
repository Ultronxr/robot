package cn.ultronxr.qqrobot.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Ultronxr
 * @date 2021/02/09 01:09
 *
 * 消息事件 - 好友消息事件监听器
 * 对好友消息中的特殊关键词进行筛选和预处理，调用对应的消息事件处理器 MsgXxxHandler
 */
@Component
@Slf4j
public class MsgFriendListener {

}
