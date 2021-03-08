package com.study.batch.part3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class TaskletProcessingConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job taskletBaseJob() {
        return jobBuilderFactory.get("taskletBaseJob")
                .incrementer(new RunIdIncrementer())
                .start(taskletBaseStep())
                .next(continuousTaskletBaseStep())
                .build();
    }

    @Bean
    public Step taskletBaseStep() {
        return stepBuilderFactory.get("taskletBaseStep")
                .tasklet(tasklet())
                .build();
    }

    @Bean
    public Step continuousTaskletBaseStep() {
        return stepBuilderFactory.get("continuousTaskletBaseStep")
                .tasklet(continuousPagingTasklet())
                .build();
    }

    private Tasklet tasklet() {
        return ((contribution, chunkContext) -> {
            List<String> items = getItems();
            log.info("task item size : {}", items.size());
            return RepeatStatus.FINISHED;
        });
    }

    private List<String> getItems() {
        ArrayList<String> items = new ArrayList<>();

        for (int i = 1; i <= 100; i++)
            items.add(String.valueOf(i));
        return items;
    }

    /**
     * Tasklet도 Chunk 처럼 Paging처리 하듯이 사용가능하다.
     */
    private Tasklet continuousPagingTasklet() {
        List<String> list = getItems();
        return (contribution, chunkContext) -> {
            StepExecution stepExecution = contribution.getStepExecution();

            int pagingSize = 10;
            int fromIndex = stepExecution.getReadCount();
            int toIndex = fromIndex + pagingSize;

            if (toIndex > list.size())
                return RepeatStatus.FINISHED;

            List<String> subList = list.subList(fromIndex, toIndex);
            subList = subList.stream().filter(it -> Integer.parseInt(it) % 2 != 0).map(it -> it += " Odd Num Spring Batch").collect(Collectors.toList());
            subList.forEach(log::info);
            stepExecution.setReadCount(toIndex);
            return RepeatStatus.CONTINUABLE;
        };
    }
}
