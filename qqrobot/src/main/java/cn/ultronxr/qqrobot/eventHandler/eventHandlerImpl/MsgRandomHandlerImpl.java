package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.ReUtil;
import cn.ultronxr.qqrobot.bean.GlobalData;
import cn.ultronxr.qqrobot.eventHandler.MsgRandomHandler;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.regex.Pattern;


/**
 * @author Ultronxr
 * @date 2021/04/23 11:49
 */
@Component
@Slf4j
public class MsgRandomHandlerImpl extends GlobalData implements MsgRandomHandler {

    private static final BigInteger NEGATIVE_INFINITY_BIG = new BigInteger("-99999999999999999999");

    private static final BigInteger POSITIVE_INFINITY_BIG = new BigInteger("99999999999999999998");

    private static final Long NEGATIVE_INFINITY_LONG = Long.MIN_VALUE;

    private static final Long POSITIVE_INFINITY_LONG = Long.MAX_VALUE - 1;


    @Override
    public void groupRandomNumberHandler(GroupMessageEvent groupMsgEvent, String msgCode, String msgContent, String msgPlain) {
        String resMsg = null;
        List<String> regexGroups = ReUtil.getAllGroups(Pattern.compile(Regex.MATH_INTERVAL), msgPlain.replaceAll(" ", ""));
        log.info("[function] 随机整数区间正则匹配结果：{}", regexGroups);

        // 下面分三种随机数命令格式，一：指定整数区间表达式；二：指定数字位数；三：不符合上述两种格式。
        if(null != regexGroups && 7 == regexGroups.size()){
            long resNumberL = randomIntervalNumber(regexGroups);
            resMsg = Long.toString(resNumberL);

            groupMsgEvent.getSubject().sendMessage(resMsg);
            log.info("[message-send] {}", resMsg);
            return;
        } else if(ReUtil.contains("\\d+", msgPlain)){
            String lengthStr = ReUtil.get("\\d+", msgPlain, 0);
            resMsg = randomLengthNumber(Integer.parseInt(lengthStr));

            groupMsgEvent.getSubject().sendMessage(resMsg);
            log.info("[message-send] {}", resMsg);
            return;
        }

        resMsg = "随机数命令格式错误！";
        groupMsgEvent.getSubject().sendMessage(resMsg);
        log.info("[message-send] {}", resMsg);
    }

    /**
     * 获取指定区间的随机数
     *
     * 这里解释使用 {@link Regex#MATH_INTERVAL} 正则表达式对 整数区间字符串 分组groups的结果
     * 例："(-11,22]"、"[-100,+999)"、"(-∞,+∞)" 分别分组groups的结果
     *          0              1            2             3               4             5             6
     *     完整匹配结果   左区间完整内容 左区间整数部分 左区间负无穷部分  右区间完整内容 右区间整数部分 右区间正无穷部分
     *   ------------------------------------------------------------------------------------------------------
     *   [   (-11,22]  ,     (-11,         -11,          null,            22],         22,           null     ]
     *   [  [-100,+999),     [-100,       -100,          null,           +999),       +999,          null     ]
     *   [    (-∞,+∞),        (-∞,        null,           -∞,             +∞),        null,           +∞      ]
     *
     *   综上，下标2、5位是数字位（可能带符号），3、6位是无穷位（一定带符号），左右区间开闭括号从1、4位截取字符获得。
     *
     * @param regexGroups   使用 {@link Regex#MATH_INTERVAL} 正则表达式对 整数区间字符串 分组groups的结果
     * @return {@code Long} 指定区间的随机数
     */
    private Long randomIntervalNumber(List<String> regexGroups) {
        // 左区间开闭括号、左区间整数、左区间负无穷、左区间负无穷部分、右区间开闭符号、右区间整数、右区间正无穷
        String leftBracket = regexGroups.get(1).substring(0, 1),
                leftNumber = regexGroups.get(2),
                leftNegativeInfinity = regexGroups.get(3),
                rightBracket = regexGroups.get(4).substring(regexGroups.get(4).length()-1),
                rightNumber = regexGroups.get(5),
                rightPositiveInfinity = regexGroups.get(6);

        // 处理数字与无穷（这里以Long的最大最小值作为正负无穷）
        // 处理数字大小问题，让左边数字一定小于右边
        long leftNumberL = (leftNegativeInfinity != null ? NEGATIVE_INFINITY_LONG : Long.parseLong(leftNumber)),
                rightNumberL = (rightPositiveInfinity != null ? POSITIVE_INFINITY_LONG : Long.parseLong(rightNumber)),
                tempL = leftNumberL;
        leftNumberL = Math.min(leftNumberL, rightNumberL);
        rightNumberL = Math.max(tempL, rightNumberL);

        // 处理开闭区间问题，这里的区间开闭取决于区间在左还是在右，而不由左右区间哪个数字更大决定，同时需要考虑下面的随机数方法的传参区间开闭
        if(leftBracket.equals("(")){
            leftNumberL += 1;
        }
        if(rightBracket.equals("]")){
            rightNumberL += 1;
        }

        // 获取Long的随机整数，这个方法是左闭右开的 [leftNumberL, rightNumberL)
        long resNumberL = RandomUtil.randomLong(leftNumberL, rightNumberL);

        log.info("[function] 随机整数决断区间：[{}, {})", leftNumberL, rightNumberL);
        log.info("[function] 随机整数结果：{}", resNumberL);
        return resNumberL;
    }

    /**
     * 获取指定数字长度位数的随机数
     *
     * @param length 指定数字长度位数
     * @return {@code String} 指定数字长度位数的随机数字符串
     */
    private String randomLengthNumber(Integer length) {
        String resNumber = RandomUtil.randomNumbers(length);

        // 如果存在前导零，就把前导零重新随机成其他数字
        if(resNumber.startsWith("0")){
            resNumber = resNumber.replaceFirst("0", RandomUtil.randomString("123456789", 1));
        }
        // 随机添加负数符号
        resNumber = (RandomUtil.randomBoolean() ? "" : "-") + resNumber;

        log.info("[function] 随机整数指定长度：{}", length);
        log.info("[function] 随机整数结果：{}", resNumber);
        return resNumber;
    }

    @Override
    public void groupRandomSecretHandler(GroupMessageEvent groupMsgEvent, String msgCode, String msgContent, String msgPlain) {

    }

    public static void main(String[] args) {

    }

}
