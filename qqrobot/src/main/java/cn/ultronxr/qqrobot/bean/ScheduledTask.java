package cn.ultronxr.qqrobot.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Calendar;
import java.util.Date;


/**
 * 定时任务Bean
 *
 * 格式与详细解释说明请参阅：
 * {@link cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl.ScheduledTaskHandlerImpl#SCHEDULED_TASK_FORMAT}
 * {@link cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl.ScheduledTaskHandlerImpl#SCHEDULED_TASK_EXPLAIN}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduledTask {

    /** 整个定时任务文字语句 */
    String fullStatement;

    /** 时间 */
    String time;

    /** 时间 */
    Date date;

    /** 时间 */
    Calendar calendar;

    /** 重复标记：false-不重复，true-重复 */
    Boolean repeat;

    /** 重复时间Cron表达式 */
    String repeatCron;

    /** 操作符：提醒 | 执行 */
    String operator;

    /** 操作对象 */
    String target;

    /** 任务内容 */
    String content;

    /** 定时任务状态：0-未执行，1-已执行，2-废弃 */
    Integer status;

}
