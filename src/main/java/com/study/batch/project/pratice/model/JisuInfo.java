package com.study.batch.project.pratice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class JisuInfo {
    /**
     * 코스닥 지수
     */
    private String kosdaqJisu;

    /**
     * 코스피 지수
     */

    private String kospiJisu;

    /**
     * 코스닥 지수 부호
     * 1. 상한 2. 상승 3. 보합 4.하한 5.하락
     */

    private String kosdaqJisuBuho;

    /**
     * 코스피 부호
     */

    private String kospiBuho;

    /**
     * 코스닥 전날 대비
     */

    private String kosdaqJisuDebi;

    /**
     * 코스피 전날 대비
     */

    private String kospiDebi;

}
