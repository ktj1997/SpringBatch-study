package com.study.batch.project.pratice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class Example implements Serializable {
    Long id;
    String value;
}
