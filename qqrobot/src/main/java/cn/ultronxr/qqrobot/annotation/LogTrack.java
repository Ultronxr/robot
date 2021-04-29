package cn.ultronxr.qqrobot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author Ultronxr
 * @date 2021/04/24 23:31
 *
 * 自定义注解 LogTrack ，用于分离出日志记录代码
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogTrack {

    /** 日志等级 */
    enum type {
        debug, info, warn, error;
    }

    type type() default type.info;

    /** 日志内容 */
    String value() default "";

}
