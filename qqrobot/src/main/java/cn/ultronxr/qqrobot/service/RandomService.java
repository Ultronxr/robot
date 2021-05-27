package cn.ultronxr.qqrobot.service;

import cn.ultronxr.qqrobot.bean.GlobalData;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2021/05/23 22:55
 */
public interface RandomService {

    /**
     * 获取指定区间的随机数
     *
     * 这里解释使用 {@link GlobalData.Regex#MATH_INTERVAL} 正则表达式对 整数区间字符串 分组groups的结果
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
     * @param regexGroups   使用 {@link GlobalData.Regex#MATH_INTERVAL} 正则表达式对 整数区间字符串 分组groups的结果
     * @return {@code Long} 指定区间的随机数
     */
    Long randomIntervalNumber(List<String> regexGroups);

    /**
     * 获取指定区间的随机数，
     * 左右端点数值都取闭区间（包含）
     *
     * @param left  区间左端点数值
     * @param right 区间右端点数值
     * @return {@code Long} 指定区间的随机数
     */
    Long randomIntervalNumber(Long left, Long right);

    /**
     * 获取指定数字长度位数的随机数
     *
     * @param length 指定数字长度位数
     * @return {@code String} 指定数字长度位数的随机数字符串
     */
    String randomLengthNumber(Integer length);

}
