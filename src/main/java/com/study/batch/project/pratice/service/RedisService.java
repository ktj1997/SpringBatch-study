package com.study.batch.project.pratice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.study.batch.project.pratice.model.Summary;

import java.util.List;

public interface RedisService {
    List<String> getInfos();

    List<Summary> getInfo(String id);

    List<Summary> insertInfo(String code) throws JsonProcessingException;
}
