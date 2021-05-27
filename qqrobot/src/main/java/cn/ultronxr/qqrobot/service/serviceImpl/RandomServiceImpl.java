package cn.ultronxr.qqrobot.service.serviceImpl;

import cn.hutool.core.util.RandomUtil;
import cn.ultronxr.qqrobot.bean.GlobalData;
import cn.ultronxr.qqrobot.service.RandomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * @author Ultronxr
 * @date 2021/05/23 22:55
 */
@Service
@Slf4j
public class RandomServiceImpl implements RandomService {

    private static final BigInteger NEGATIVE_INFINITY_BIG = new BigInteger("-99999999999999999999");

    private static final BigInteger POSITIVE_INFINITY_BIG = new BigInteger("99999999999999999998");

    private static final Long NEGATIVE_INFINITY_LONG = Long.MIN_VALUE;

    private static final Long POSITIVE_INFINITY_LONG = Long.MAX_VALUE - 1;


    @Override
    public Long randomIntervalNumber(List<String> regexGroups) {
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

        if(leftNumberL == rightNumberL){
            log.info("[Function] 区间左右数字相等，随机整数结果：{}", leftNumberL);
            return leftNumberL;
        }

        // 处理开闭区间问题，这里的区间开闭取决于区间在左还是在右，而不由左右区间哪个数字更大决定，同时需要考虑下面的随机数方法的传参区间开闭
        if(leftBracket.equals("(")){
            leftNumberL += 1;
        }
        if(rightBracket.equals("]")){
            rightNumberL += 1;
        }

        // 获取Long的随机整数，这个方法是左闭右开的 [leftNumberL, rightNumberL)
        long resNumberL = RandomUtil.randomLong(leftNumberL, rightNumberL);

        log.info("[Function] 随机整数决断区间：[{}, {})", leftNumberL, rightNumberL);
        log.info("[Function] 随机整数结果：{}", resNumberL);
        return resNumberL;
    }

    @Override
    public Long randomIntervalNumber(Long left, Long right) {
        if(right != Long.MAX_VALUE) {
            right += 1;
        }
        return RandomUtil.randomLong(left, right);
    }

    @Override
    public String randomLengthNumber(Integer length) {
        String resNumber = RandomUtil.randomNumbers(length);

        // 如果存在前导零，就把前导零重新随机成其他数字
        if(resNumber.startsWith("0")){
            resNumber = resNumber.replaceFirst("0", RandomUtil.randomString("123456789", 1));
        }
        // 随机添加负数符号
        // resNumber = (RandomUtil.randomBoolean() ? "" : "-") + resNumber;

        log.info("[Function] 随机整数指定长度：{}", length);
        log.info("[Function] 随机整数结果：{}", resNumber);
        return resNumber;
    }

}
