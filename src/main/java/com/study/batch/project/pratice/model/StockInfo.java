package com.study.batch.project.pratice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockInfo {

    /**
     * 종목 이름
     */
    private String jongName;

    /**
     * 현재 주가
     */
    private String curJuka;

    /**
     * 전날 종가
     */
    private String prevJuka;

    /**
     * 시가
     */
    private String startJuka;

    /**
     * 고가
     */
    private String highJuka;

    /**
     * 저가
     */
    private String lowJuka;

    /**
     * 상한가
     */
    private String upJuka;

    /**
     * 하한가
     */
    private String downJuka;

    /**
     * 액면가
     */
    private String faceJuka;

    /**
     * 거래량
     */
    private String volume;

    /**
     * 거래대금
     */
    private String money;
    /**
     * 등락 부호
     * 1. 상한 2. 상승 3. 보합 4.하한 5.하락
     */
    private String dungRak;
    /**
     * 전날 대
     */
    private String debi;
}
