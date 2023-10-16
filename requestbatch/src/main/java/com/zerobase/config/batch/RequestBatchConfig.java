package com.zerobase.config.batch;

import com.zerobase.listener.JobLoggerListener;
import com.zerobase.model.request.Request;
import com.zerobase.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableBatchProcessing
@Slf4j
@RequiredArgsConstructor
public class RequestBatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final RequestRepository requestRepository;

    private static final int CHUNK_SIZE = 1000;

    @Bean
    public Job deleteRequestJob() {
        return jobBuilderFactory.get("deleteRequestJob")
                .listener(new JobLoggerListener())
                .start(deleteRequestStep())
                .build();
    }

    @Bean
    public Step deleteRequestStep() {
        return stepBuilderFactory.get("deleteRequestStep")
                .allowStartIfComplete(true)
                .<Request, Request>chunk(CHUNK_SIZE)
                .reader(requestItemReader())
                .writer(requestItemWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<Request> requestItemReader() {
        return new JpaPagingItemReaderBuilder<Request>()
                .name("requestItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(CHUNK_SIZE)
                .queryString("select r from Request r where r.dmRoom IS NULL AND r.isFinished = false AND :dayAgo > r.createdAt")
                .parameterValues(Collections.singletonMap("dayAgo", LocalDateTime.now().minusDays(1)))
                .build();
    }

    @Bean
    public ItemWriter<Request> requestItemWriter() {
        return new ItemWriter<Request>() {
            @Override
            public void write(List<? extends Request> items) throws Exception {
                requestRepository.deleteAll(items);
            }
        };
    }

}
