package com.study.batch.project.pratice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    /**
     * Redis서버와 통신을 위한 low-level 추상화
     * 설정에 따라서 이미존재하는 connection을 리턴하거나, 새로운 connection을 리턴할 수 있다.
     * Exception 발생 시 --> SpringDataAccessException
     * 클라이언트 라이브러리로는 Jedis나 Lettuce가 많이쓰인다.
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host,port);
    }

    /**
     * Redis 서버에서 Command를 수행하기위한 high-level 추상화
     * Object Serialization과 Connection Management 수행
     * Redis서버 데이터에 CRUD를 하기위한 KeyType Operation과 KeyBound Operaion 인터페이스 제공
     * Thread-Safe하며, 재사용 가능
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        return redisTemplate;
    }

    /**
     * Redis에 저장된 Key값이 String이 되도록 하는 것이 일반적이므로, StringRedisTemplate를 사용할 때가 많다.
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
        stringRedisTemplate.setValueSerializer(new StringRedisSerializer());
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory());

        return stringRedisTemplate;
    }
}
