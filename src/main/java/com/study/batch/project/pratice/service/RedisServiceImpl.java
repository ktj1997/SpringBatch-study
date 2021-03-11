package com.study.batch.project.pratice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.study.batch.project.pratice.model.Info;
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
    private final StockAPIService stockAPIService;

    @Override
    public List<String> getInfos() {
        return new ArrayList<>(stringRedisTemplate.keys("*"));
    }

    @Override
    public List<String> getInfo(String id) {
        ListOperations<String, String> listOperations = stringRedisTemplate.opsForList();
        return listOperations.range(String.valueOf(id), 0, -1);
    }

    @Override
    public List<String> insertInfo(String code) throws JsonProcessingException {
        ListOperations<String, String> listOperations = stringRedisTemplate.opsForList();
        listOperations.rightPush(code, stockAPIService.requestApi(code));
        return listOperations.range(code, 0, -1);
    }
}
