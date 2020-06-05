package com.frankmoley.lil.fid.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Component
@Aspect
public class LoggingAspect {

    private static  final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);
    int i = 0;

    @Pointcut("@annotation(Loggable)")
    public void executeLogging(){}

    @Pointcut("@annotation(Counterable)")
    public void executeCounter(){

    }

//    @Before("executeLogging()")
//    public void logMethodCall(JoinPoint joinPoint){
//        StringBuilder stringBuilder = new StringBuilder("Method: ");
//
//        stringBuilder.append(joinPoint.getSignature().getName());
//        Object[] args = joinPoint.getArgs();
//        if(null != args && args.length > 0){
//            stringBuilder.append(" args=[ | " );
//            Arrays.asList(args).forEach(arg -> {
//                stringBuilder.append(arg).append(" | ");
//            });
//            stringBuilder.append("]");
//        }
//
//        LOGGER.info(stringBuilder.toString());
//    }

//    @AfterReturning(value = "executeLogging()", returning = "returnValue")
//    public void logMethodCall(JoinPoint joinPoint, Object returnValue){
//        StringBuilder stringBuilder = new StringBuilder("Method: ");
//
//        stringBuilder.append(joinPoint.getSignature().getName());
//        Object[] args = joinPoint.getArgs();
//        if(null != args && args.length > 0){
//            stringBuilder.append(" args=[ | " );
//            Arrays.asList(args).forEach(arg -> {
//                stringBuilder.append(arg).append(" | ");
//            });
//            stringBuilder.append("]");
//        }
//
//        if(returnValue instanceof Collection){
//            stringBuilder.append(" returning: ").append(((Collection)returnValue).size()).append(" instance(ss)");
//        } else {
//
//            stringBuilder.append(" returning: ").append(returnValue.toString());
//        }
//        LOGGER.info(stringBuilder.toString());
//    }


    @Around(value = "executeLogging()")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object returnValue = joinPoint.proceed();
        long totalTime = System.currentTimeMillis() - startTime;
        StringBuilder stringBuilder = new StringBuilder(" Method: ");
        stringBuilder.append(" total time= ").append(totalTime).append("milliseconds");

        stringBuilder.append(joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();
        if(null != args && args.length > 0){
            stringBuilder.append(" args=[ | " );
            Arrays.asList(args).forEach(arg -> {
                stringBuilder.append(arg).append(" | ");
            });
            stringBuilder.append("]");
        }

        if(returnValue instanceof Collection){
            stringBuilder.append(" returning: ").append(((Collection)returnValue).size()).append(" instance(ss)");
        } else {

            stringBuilder.append(" returning: ").append(returnValue.toString());
        }
        LOGGER.info(stringBuilder.toString());
        return returnValue;
    }
}
