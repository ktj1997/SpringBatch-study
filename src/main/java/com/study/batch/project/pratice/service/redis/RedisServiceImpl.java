package com.study.batch.project.pratice.service.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.batch.project.pratice.model.Summary;
import com.study.batch.project.pratice.service.stock.StockAPIService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final StringRedisTemplate stringRedisTemplate;
    private final StockAPIService stockAPIService;
    private final ObjectMapper objectMapper;

    @Override
    public List<String> getInfos() {
        return new ArrayList<>(stringRedisTemplate.keys("*"));
    }

    @Override
    public List<Summary> getInfo(String id) {
        ListOperations<String, String> listOperations = stringRedisTemplate.opsForList();
        List<Summary> infos = listOperations.range(String.valueOf(id), 0, -1).stream().map(it -> {
            try {
                return objectMapper.readValue(it, Summary.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        return infos;
    }

    @Override
    public List<Summary> insertInfo(String code) throws JsonProcessingException {
        ListOperations<String, String> listOperations = stringRedisTemplate.opsForList();
        listOperations.rightPush(code, stockAPIService.requestApi(code));
        return listOperations.range(code, 0, -1).stream().map(it -> {
            try {
                return objectMapper.readValue(it, Summary.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
    }
}
