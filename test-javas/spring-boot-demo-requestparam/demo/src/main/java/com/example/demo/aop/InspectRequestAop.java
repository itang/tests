package com.example.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.annotation.Annotation;

@Aspect
@Component
@Slf4j
public class InspectRequestAop {

    //切点: 执行 com.example.demo下 任意类， 任意方法， 任意参数， 任意返回值
    @Pointcut("execution (* com.example.demo.*.*(..))")
    public void testPointcut() {
    }

    @Around("testPointcut()")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
        Object[] args = proceedingJoinPoint.getArgs();
        log.debug("args: {}", args);
        String kind = proceedingJoinPoint.getKind();
        log.debug("kind: {}", kind);

        Signature signature = proceedingJoinPoint.getSignature();
        log.debug("signature: {}", signature);

        if (proceedingJoinPoint instanceof MethodInvocationProceedingJoinPoint) {
            MethodSignature s = (MethodSignature) signature;
            Annotation[] ans = s.getMethod().getDeclaredAnnotations();
            log.debug("method ans: {}", ans);

            GetMapping annotation = s.getMethod().getAnnotation(GetMapping.class);
            log.debug("getmapping:{}", annotation);
        }


        Object ret = null;
        try {
            ret = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        log.debug("ret: {}", ret);
        return ret;
    }
}
