package cn.ultronxr.qqrobot.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.lang.reflect.Modifier;
import java.util.Arrays;


/**
 * @author Ultronxr
 * @date 2021/04/24 23:39
 *
 *
 */
@Slf4j
//@Component
@Aspect
@EnableAspectJAutoProxy(proxyTargetClass = false, exposeProxy = false)
public class LogTrackAspectSendMsg {

    //@Pointcut("execution(public * cn.ultronxr.qqrobot.service.serviceImpl.TestServiceImpl.*(..))")
    //@Pointcut("execution(public * net.mamoe.mirai.contact.Contact.sendMessage(..))")
    @Pointcut("execution(* net.mamoe.mirai.contact.Contact.sendMessage(..))")
    //@Pointcut("target(java.lang.StringBuilder))")
    //@Pointcut("execution(* java.lang.StringBuilder.*(..))")
    //@Pointcut("within(java.lang.StringBuilder)")
    public void pointCutSendMsg() {
    }

    @Around("pointCutSendMsg()")
    public Object aroundSendMsg(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
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
        log.info("[LogTrackSendMsg] {}", logSourceMethod);
        log.info("[LogTrackSendMsg] args = {}", Arrays.toString(proceedingJoinPoint.getArgs()));

        //Parameter[] parameters = methodSig.getMethod().getParameters();

        return proceedingJoinPoint.proceed();
    }

    //@Before("pointcutTest()")
    //public void beforeTest(JoinPoint joinPoint) {
    //}
    //
    //@AfterReturning(pointcut = "pointcutTest()", returning = "object")
    //public void afterReturningTest(Object object) {
    //}
    //
    //@AfterThrowing(pointcut = "pointcutTest()", throwing = "exception")
    //public void afterThrowingTest(Exception exception) {
    //}
    //
    //@After("pointcutTest()")
    //public void afterTest(JoinPoint joinPoint) {
    //    log.info("[LogTrackSendMsg] 消息发送完成。");
    //}


    /////////////proxyFactory
    //AspectJProxyFactory proxyFactory = new AspectJProxyFactory();
    //Contact contact = groupMsgEvent.getGroup();
    //contact.sendMessage("no proxy");
    //
    //proxyFactory.setProxyTargetClass(false);
    //proxyFactory.setExposeProxy(false);
    //proxyFactory.setTarget(contact);
    //proxyFactory.addAspect(LogTrackAspectSendMsg.class);
    //
    //Contact proxy = proxyFactory.getProxy();
    //proxy.sendMessage("with proxy");



}
