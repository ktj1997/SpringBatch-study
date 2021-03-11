package com.study.batch.project.pratice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Info {
    Long id;
    List<String> values;
}
