package com.study.batch.project.pratice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Summary {
    private String queryTime;

    private Stock Tbl_StockInfo;

    private Market stockInfo;
}
