package com.study.batch.project.pratice.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.study.batch.project.pratice.model.Response;
import com.study.batch.project.pratice.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class RedisController {

    private final RedisService redisService;

    @GetMapping(value = "/infos",produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public Response getInfos() {

        return new Response(HttpStatus.OK.value(), redisService.getInfos());
    }

    @GetMapping(value = "/infos/{id}",produces ={MediaType.APPLICATION_JSON_VALUE} )
    @ResponseStatus(HttpStatus.OK)
    public Response getInfo(@PathVariable String id) {
        return new Response(HttpStatus.OK.value(), redisService.getInfo(id));
    }

    @PostMapping(value = "/infos/{id}" ,produces ={MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public Response insertInfo(@PathVariable String id) throws JsonProcessingException {

        return new Response(HttpStatus.CREATED.value(), redisService.insertInfo(String.valueOf(id)));
    }
}
