package com.study.batch.project.pratice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class Response<T> {
    int status;
    T data;
    public Response(int status, T data){
        this.status = status;
        this.data = data;
    }

}
