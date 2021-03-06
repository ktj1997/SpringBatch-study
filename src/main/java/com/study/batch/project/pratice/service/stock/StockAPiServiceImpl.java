package com.study.batch.project.pratice.service.stock;

;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.study.batch.project.pratice.model.Summary;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class StockAPiServiceImpl implements StockAPIService {

    @Value("${stock.api.host}")
    private String host;

    private final RestTemplate restTemplate;
    private final XmlMapper xmlMapper;
    private final ObjectMapper objectMapper;


    @Override
    public String requestApi(String code) throws JsonProcessingException {
        String str = restTemplate.getForObject(host + code, String.class).trim();
        Summary summary = xmlMapper.readValue(str, Summary.class);
        String json = objectMapper.writeValueAsString(summary);
        return json;
    }
}
