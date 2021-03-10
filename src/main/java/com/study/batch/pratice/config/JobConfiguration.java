package com.study.batch.pratice.config;

import com.study.batch.pratice.model.Example;
import com.study.batch.pratice.service.RedisService;
import com.study.batch.pratice.util.DateUtil;
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
                .<Example, Example>chunk(chunkSize)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<Example> itemReader() {
        return new ListItemReader<>(getItems());
    }

    @Bean
    @StepScope
    public ItemProcessor<Example, Example> itemProcessor() {
        return item -> {
            item.setValue(DateUtil.parseToString(new Date()) + ";" + item.getValue());
            return item;
        };
    }

    @Bean
    @StepScope
    public ItemWriter<Example> itemWriter() {
        return items -> items.forEach(it -> {
            redisService.insertInfo(it.getId(), it.getValue());
        });
    }

    private List<Example> getItems() {
        List<Example> list = new ArrayList<>();
        for (int i = 1; i <= 2000; i++) {
            list.add(new Example((long) i, UUID.randomUUID().toString()));
        }
        return list;
    }
}
