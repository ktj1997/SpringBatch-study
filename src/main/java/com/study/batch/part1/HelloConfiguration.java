package com.study.batch.part1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class HelloConfiguration {
    /**
     * SpringBatch에 의해서 이미 Bean으로 선언되어있다.
     * Batch에 필요한 Bean들을 만들어내기위한 Factory Bean
     */
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job helloJob() {
        /**
         * .get(name)의 name은 SpringBatch의 실행을 가능하게 하는 Key값
         */
        return jobBuilderFactory.get("helloJob")
                /**
                 *  Job이 실행 할때마다 ParameterId 자동생성
                 *  Auto_increment라고 보면될듯
                 */
                .incrementer(new RunIdIncrementer())
                /**
                 * start는 Job실행 시에 최초로 실행될 Method
                 */
                .start(this.helloStep())
                .build();
    }

    /**
     * Step은 Job의 실행 단위
     * Job은 한개 이상의 Step을 가질 수 있다.
     * tasklet(간단한 작업)은 Step의 실행 단위 (Chunk: 대규모 처리)
     */
    @Bean
    public Step helloStep() {
        return stepBuilderFactory.get("helloStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info("hello Spring Batch");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
