package com.study.batch.pratice.service;

import com.study.batch.pratice.model.Info;

import java.util.List;

public interface RedisService {
    List<String> getInfos();

    Info getInfo(Long id);

    Info insertInfo(Long id, String value);
}
