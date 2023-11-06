package com.saber.spring_boot_camel_test.advice;

import com.saber.spring_boot_camel_test.dto.hi.HelloRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class HelloServiceAspect {

    @Around(value = "@annotation(com.saber.spring_boot_camel_test.annotations.SayHelloAnnotation)")
    public Object aroundSayHello(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("say Hello aspect call ...........");
        if (joinPoint.getArgs().length > 0) {
            Object body = joinPoint.getArgs()[0];
            if (body instanceof HelloRequestDto) {
                HelloRequestDto dto = (HelloRequestDto) body;
                log.info("sayHello dto ===> {}", dto);
            }
        }
        Object result = joinPoint.proceed();
        log.info("result ====> {}", result);
        return result;
    }
}
