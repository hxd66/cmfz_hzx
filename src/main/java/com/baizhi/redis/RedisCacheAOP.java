package com.baizhi.redis;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Method;

@Configuration
@Aspect
public class RedisCacheAOP {
    @Autowired
    private Jedis jedis;

    @Around("execution(* com.baizhi.service.impl.*.select*(..)) || execution(* com.baizhi.service.impl.*.get*(..))")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //判断注解是否存在
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        boolean b = method.isAnnotationPresent(RedisCache.class);
        Object o = null;
        if (b) {
            //如果注解存在
            StringBuilder stringBuilder = new StringBuilder();
            //获取类的名称
            String className = proceedingJoinPoint.getTarget().getClass().getName();
            //获取方法名称
            String methodName = proceedingJoinPoint.getSignature().getName();
            stringBuilder.append(methodName);
            //获取参数实参
            Object[] args = proceedingJoinPoint.getArgs();
            for (Object arg : args) {
                stringBuilder.append(arg);
            }
            String s = stringBuilder.toString();
            //判断键是否存在
            System.out.println(jedis.hexists(className, s));
            if (jedis.hexists(className, s)) {
                String hget = jedis.hget(className, s);
                o = JSONObject.parse(hget);
            } else {
                o = proceedingJoinPoint.proceed();
                jedis.hset(className, s, JSONObject.toJSONString(o));
            }


        } else {
            //注解不存在
            o = proceedingJoinPoint.proceed();
        }
        jedis.close();
        return o;
    }

    int i = 0;

    @AfterReturning("execution(* com.baizhi.service.impl.*.*(..)) &&!execution(* com.baizhi.service.impl.*.select*(..))&&!execution(* com.baizhi.service.impl.*.get*(..))")
    public void after(JoinPoint joinPoint) {
        String name = joinPoint.getTarget().getClass().getName();
        jedis.del(name);
        jedis.close();
    }
}
