package com.study.batch.pratice.service;

import com.study.batch.pratice.model.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public List<String> getInfos() {
        return new ArrayList<>(stringRedisTemplate.keys("*"));
    }

    @Override
    public Info getInfo(Long id) {
        ListOperations<String, String> listOperations = stringRedisTemplate.opsForList();
        return new Info(id,listOperations.range(String.valueOf(id), 0, -1));
    }

    @Override
    public Info insertInfo(Long id, String value) {
        ListOperations<String, String> listOperations = stringRedisTemplate.opsForList();
        listOperations.rightPush(String.valueOf(id), value);
        return new Info(id,listOperations.range(String.valueOf(id), 0, -1));
    }
}
