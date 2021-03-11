package com.study.batch.project.pratice.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface StockAPIService {
    public String requestApi(String code) throws JsonProcessingException;
}
