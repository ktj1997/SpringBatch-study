package com.study.batch.project.pratice.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.study.batch.project.pratice.model.Example;
import com.study.batch.project.pratice.service.RedisService;
import com.study.batch.project.pratice.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobConfiguration {
    private int chunkSize = 100;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final RedisService redisService;

    @Bean
    public Job sequenceJob() {
        return jobBuilderFactory.get("sequenceJob")
                .start(sequenceStep())
                .build();
    }

    @Bean
    @JobScope
    public Step sequenceStep() {
        return stepBuilderFactory.get("record")
                .<String, String>chunk(chunkSize)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<String> itemReader() {
        return new ListItemReader<>(getItems());
    }

    @Bean
    @StepScope
    public ItemProcessor<String, String> itemProcessor() {
        return item -> item;
    }

    @Bean
    @StepScope
    public ItemWriter<String> itemWriter() {
        return items -> items.forEach(it -> {
            try {
                redisService.insertInfo(it);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }

    private List<String> getItems() {
        return List.of("000020", "000050", "003480", "000950", "004250", "001680");
    }
}