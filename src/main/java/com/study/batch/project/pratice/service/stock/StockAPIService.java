package com.study.batch.project.pratice.service.stock;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface StockAPIService {
    public String requestApi(String code) throws JsonProcessingException;
}
