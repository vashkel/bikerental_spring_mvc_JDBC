package com.example.bikerental.aspects;


import com.example.bikerental.util.Authorization;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
public class MyLogger {

private final Authorization authorization;

    private final Logger log = LoggerFactory.getLogger(this.getClass());



    @Autowired
    public MyLogger(Authorization authorization) {
        this.authorization = authorization;
    }

   @Before("execution(* com.example.bikerental.*(..))" )
    public void beforeMethodInvocation(JoinPoint joinPoint) {
       log.error("Authorized : " + authorization.getAuthorized());
       log.error("**************************");
       log.error("log: before method" + joinPoint.getSignature().toString() + " was invoked");
       log.error("*****************************");
    }
}
