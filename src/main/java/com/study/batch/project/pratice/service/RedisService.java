package com.study.batch.project.pratice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.study.batch.project.pratice.model.Info;

import java.util.List;

public interface RedisService {
    List<String> getInfos();

    List<String> getInfo(String id);

    List<String> insertInfo(String code) throws JsonProcessingException;
}
