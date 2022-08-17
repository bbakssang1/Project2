package com.rooke.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LoggerAspect {

  @Around("execution(* com.rooke.controller..*Controller.*(..)) || execution(* com.rooke.service..*Service.*(..)) || execution(* com.rooke.mapper..*Mapper.*(..))")
  public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
    String name = joinPoint.getSignature().getDeclaringTypeName();
    String type = "";

    if (name.contains("Controller")) {
      type = "Controller==>";
    } else if (name.contains("Service")) {
      type = "ServiceImpl==>";
    } else if (name.contains("Mapper")) {
      type = "Mapper==>";
    }
    log.debug(type + name + "." + joinPoint.getSignature().getName() + "()");
    return joinPoint.proceed();
  }

}
