package com.frankmoley.lil.fid.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class CountingAspect {

    private static final Logger LOGGER  = LoggerFactory.getLogger(CountingAspect.class);
    private static final Map<String, Integer> map = new HashMap<>();
    int i = 0;

    @Pointcut("@annotation(Counterable)")
    public void executeCounting(){}

    @Around("executeCounting()")
    public Object counterMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        if(map.containsKey(methodName)){
            Integer integer = map.get(methodName);
            integer++;
            map.put(methodName, integer);
        } else
            map.put(methodName, 1);
        Object proceed = joinPoint.proceed();
        StringBuilder stringBuilder = new StringBuilder("Current counter counts are: | ");
        map.forEach((k,v)-> stringBuilder.append(k + "::" + v + " | "));
        LOGGER.info(stringBuilder.toString());
        return proceed;
    }
}
