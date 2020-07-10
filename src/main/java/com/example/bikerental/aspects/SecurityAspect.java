package com.example.bikerental.aspects;

import com.example.bikerental.exception.NotAuthorizedException;
import com.example.bikerental.util.Authorization;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;


@Aspect
@Configuration
public class SecurityAspect {

    private Authorization authorization;
    @Autowired
    public SecurityAspect(Authorization authorization) {
        this.authorization = authorization;
    }

    @Pointcut("within(com.example.bikerental.controller..*)")
    public void inControllerLayer() {

    }

    @Pointcut("  !execution(* com.example.bikerental.controller.UserController.loginForm(..)) " +
            " && !execution(* com.example.bikerental.controller.UserController.login(..))" +
            " && !execution(* com.example.bikerental.controller.UserController.loginPage(..))")
    public static void notSuitableMethods() {

    }


    @Before("inControllerLayer() && notSuitableMethods()")
    public void checkAuthorize() throws NotAuthorizedException {
        if (!authorization.getAuthorized()) {
            throw new NotAuthorizedException("User is not authorized");
        }
    }


}
