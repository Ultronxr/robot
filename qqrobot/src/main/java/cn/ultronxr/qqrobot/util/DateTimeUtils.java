package cn.ultronxr.qqrobot.util;

import cn.hutool.core.date.CalendarUtil;

import java.util.Calendar;


/**
 * 有关日期和时间的自定义工具类
 */
public class DateTimeUtils {


    /**
     * 修改Calendar对象中的当天时间
     *
     * @param calendar    修改calendar对象
     * @param hourOfDay   小时，24小时制
     * @param minute      分钟
     * @param second      秒
     * @param milliSecond 毫秒
     */
    public static void setCalendarTime(Calendar calendar, Integer hourOfDay, Integer minute, Integer second, Integer milliSecond){
        if(null == calendar){
            return;
        }
        if(null != hourOfDay && hourOfDay >= 0 && hourOfDay <= 24){
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        }
        if(null != minute && minute >= 0 && minute <= 59){
            calendar.set(Calendar.MINUTE, minute);
        }
        if(null != second && second >= 0 && second <= 59){
            calendar.set(Calendar.SECOND, second);
        }
        if(null != milliSecond && milliSecond >= 0 && milliSecond <= 1000){
            calendar.set(Calendar.MILLISECOND, milliSecond);
        }
    }

    /**
     * 检查 一个时间 处于其当天的哪个 整点时间段
     *
     * 时间段编号：
     *   00:00 - xx:00 - xx:00 - xx:00 - ... - 24:00
     *         0       1       2         ...
     *
     * @param calendar      待检查的时间
     * @param increaseHours 递增的整点小时节点（24小时制，取值区间 [1,23] ）
     *                      0时和24时已经隐含地自动检查了，无需在数组首尾填写！
     * @return 时间段编号
     */
    public static Integer checkTimeHourPeriod(Calendar calendar, int[] increaseHours){
        if(null == calendar || null == increaseHours || 0 == increaseHours.length){
            return -1;
        }
        Calendar calendarHour = CalendarUtil.calendar(calendar.getTime());
        for(int i = 0; i < increaseHours.length; i++){
            setCalendarTime(calendarHour, increaseHours[i], 0, 0, 0);
            if(calendar.before(calendarHour)){
                return i;
            }
        }
        return increaseHours.length;
    }

    /**
     * 检查指定日期是否是周末（包括周六和周日）
     *
     * @param calendar 待检查的日期
     * @return true-是周末，false-不是周末
     */
    public static Boolean isWeekend(Calendar calendar){
        return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

}
