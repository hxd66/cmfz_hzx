package com.baizhi.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class CommonFactory {
    @Bean
    public Jedis getJedis() {
        return new Jedis("192.168.100.31", 7000);//redis服务的机器ip，端口号
    }
}
