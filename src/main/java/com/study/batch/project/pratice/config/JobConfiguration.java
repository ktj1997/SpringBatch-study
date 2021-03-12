package com.study.batch.project.pratice.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.study.batch.project.pratice.service.redis.RedisService;
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

import java.util.List;

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
        return List.of("243880", "243890", "364980", "305720", "304780");
    }
}
