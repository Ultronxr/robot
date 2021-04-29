package cn.ultronxr.qqrobot.aspect;

import cn.ultronxr.qqrobot.annotation.LogTrack;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Modifier;
import java.util.Arrays;


/**
 * @author Ultronxr
 * @date 2021/04/25 16:10
 *
 * 针对LogTrack注解的AOP切面
 */
@Component
@Aspect
@Slf4j
public class LogTrackAspect {

    @Pointcut("@annotation(cn.ultronxr.qqrobot.annotation.LogTrack)")
    public void pointcut(){
    }

    @Around("@annotation(logTrack)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, LogTrack logTrack) throws Throwable {
        log.info("around");
        MethodSignature methodSig = (MethodSignature) proceedingJoinPoint.getSignature();
        String logSourceMethod = proceedingJoinPoint.getSignature().getDeclaringTypeName() +
                "." +
                Modifier.toString(proceedingJoinPoint.getSignature().getModifiers()) +
                " " +
                methodSig.getReturnType().getName() +
                " " +
                proceedingJoinPoint.getSignature().getName() +
                "(" +
                Arrays.toString(methodSig.getMethod().getParameters()) +
                ")";
        log.info("[LogTrack] method - {}", logSourceMethod);
        log.info("[LogTrack] args - {}", Arrays.toString(proceedingJoinPoint.getArgs()));
        logLogTrack(logTrack);
        return proceedingJoinPoint.proceed();
    }

    //@Before("pointcut()")
    //public void before(JoinPoint joinPoint) {
    //}
    //
    //@AfterReturning(pointcut = "pointcut()", returning = "object")
    //public void afterReturning(Object object) {
    //}
    //
    //@AfterThrowing(pointcut = "pointcut()", throwing = "exception")
    //public void afterThrowing(Exception exception) {
    //}
    //
    //@After("pointcut()")
    //public void after(JoinPoint joinPoint) {
    //}

    private void logLogTrack(LogTrack logTrack){
        switch (logTrack.type()){
            case debug: log.debug("[LogTrack] {}", logTrack.value()); break;
            case info:  log.info("[LogTrack] {}", logTrack.value());  break;
            case warn:  log.warn("[LogTrack] {}", logTrack.value());  break;
            case error: log.error("[LogTrack] {}", logTrack.value()); break;
        }
    }

}
