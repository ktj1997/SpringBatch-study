package com.study.batch.project.pratice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockPrice {

    private StockInfo Tbl_StockInfo;

    private JisuInfo stockInfo;
}
