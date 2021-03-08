package com.study.batch.part3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ChunkProcessingConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job chunkBaseJob() {
        return jobBuilderFactory.get("chunkBaseJob")
                .incrementer(new RunIdIncrementer())
                .start(chunkBaseStep()).build();
    }

    @Bean
    public Step chunkBaseStep() {
        return stepBuilderFactory.get("chunkBaseStep")
                /**
                 * 전체 데이터 / chunkSize --> 한번에 처리 할 데이터 양
                 * Paging과 유사
                 */
                .<String, String>chunk(10)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    /**
     * 데이터를 읽는 스트림의 역할
     * ItemReader에서 null을 리턴하면 Chunk가 종료된다.(더 이상 읽을게 없는 경우)
     */
    private ItemReader<String> itemReader() {
        return new ListItemReader<>(getItems());
    }

    /**
     * ItemProcessor는 ItemReader에서 읽어온 것을 가공하거나,
     * ItemWriter로 넘길지 말지를 결정한다. (null 이면 ItemWriter에게 넘어가지 않는다)
     * ItemProcessor<I,O> // O process(I)
     * ItemProcessor에는 Process 메소드 하나만 존재하기 떄문에 람다식 사용가능
     */
    private ItemProcessor<String, String> itemProcessor() {
        return item -> Integer.parseInt(item) % 2 == 0 ? null : item + " Odd Num Spring Batch";
    }

    /**
     * List 타입의 Processing의 결과물을 얻게된다.
     */
    private ItemWriter<String> itemWriter() {
        return items -> items.forEach(log::info);
    }

    private List<String> getItems() {
        ArrayList<String> items = new ArrayList<>();

        for (int i = 1; i <= 100; i++)
            items.add(String.valueOf(i));
        return items;
    }
}
