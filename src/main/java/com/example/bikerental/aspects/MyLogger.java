package com.example.bikerental.aspects;


import com.example.bikerental.util.Authorization;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;


@Aspect
@Configuration
public class MyLogger {

    private Authorization authorization;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public MyLogger(Authorization authorization) {
        this.authorization = authorization;

    }

    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            "|| within(@org.springframework.stereotype.Service *)" +
            "|| within(@org.springframework.stereotype.Controller *)")
    public static void springBeanPointcut() { 

    }

    @Pointcut("within(com.example.bikerental..*)" +
            " ||within(com.example.bikerental.dao..*)" +
            " ||within(com.example.bikerental.service..*)" +
            " || within(com.example.bikerental.controller..*)")
    public static void applicationPackagePointcut() {
    }


    @Before("execution(* com.example.bikerental.controller.*.*(..))")
    public void beforeMethodInvocation(JoinPoint joinPoint) {
        log.error("Authorized : " + authorization.getAuthorized());
        log.debug("**************************");
        log.debug("log: before method " + joinPoint.getSignature().toString() + " was invoked");
        log.debug("*****************************");
    }

    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (log.isDebugEnabled()) {
            log.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        }
        try {
            Object result = joinPoint.proceed();
            if (log.isDebugEnabled()) {
                log.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(), result);
            }
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            throw e;
        }
    }

    @AfterReturning("within(com.example.bikerental.dao..*) && args(result)")
    public void afterReturn(JoinPoint joinPoint, Object result) {
        log.info("**************************");
        log.info("************** LOG : Error************");
        log.error("Result of method invocation : " + result);
        log.error("*****************************");
    }


    @AfterThrowing(pointcut = "within(@org.springframework.web.bind.annotation.RequestMapping *)", throwing = "e")
    public void logAfterThrowing(Throwable e) {
        log.error("**************************");
        log.error("************** LOG : Error************");
        log.error("Exception : " + e.getMessage());
        log.error("*****************************");
    }
}
