package com.baizhi.redis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//指明当前注解生效的位置
@Target(ElementType.METHOD)
//知名当前注解的生效时机
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCache {

}
