package org.zerock.aop;

import lombok.extern.log4j.Log4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Log4j
@Component
public class LogAdvice {

    @Before("execution(* org.zerock.service.SampleService.*(..))")
    public void logBefore(){
        System.out.println("!!!!!!");
        log.info("=================================");

    }

    @Before("execution(* org.zerock.service.SampleService*.doAdd(String, String))&& args(str1, str2)")
    public void logBeforeWithParam(String str1, String str2) {
        //log.info("str1:" + str1);
        //log.info("str2:" + str2);
        System.out.println("str1:" + str1);
        System.out.println("str2:" + str2);
    }

    @AfterThrowing(pointcut = "execution(* org.zerock.service.SampleService*.*(..))", throwing = "exception")
    public void logException(Exception exception) {
        //log.info("Exception...!!!!!");
        //log.info("exception:" + exception);
        System.out.println("Exception...!!!!!");
        System.out.println("exception:" + exception);
    }

    @Around("execution(* org.zerock.service.SampleService*.*(..))")
    public Object logTime(ProceedingJoinPoint pjp) {

        long start = System.currentTimeMillis();

        //log.info("Target : " + pjp.getTarget());
        //log.info("Param : " + Arrays.toString(pjp.getArgs()));
        System.out.println("Target : " + pjp.getTarget());
        System.out.println("Param : " + Arrays.toString(pjp.getArgs()));

        //invoke method
        Object result = null;

        try{
            result = pjp.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        //log.info("TIME : " + (end-start));
        System.out.println("TIME : " + (end-start));

        return result;
    }
}
