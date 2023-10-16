package com.zerobase.config.batch;

import com.zerobase.listener.JobLoggerListener;
import com.zerobase.model.dm.dm.domain.model.Dm;
import com.zerobase.model.dm.room.domain.model.DmRoom;
import com.zerobase.repository.DmRepository;
import com.zerobase.repository.DmRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Configuration
@EnableBatchProcessing
@Slf4j
@RequiredArgsConstructor
public class DmRoomBatchConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final DmRepository dmRepository;
    private final DmRoomRepository dmRoomRepository;

    private static final int CHUNK_SIZE = 1000;

    @Bean
    public Job deleteDmRoomAndDmJob() {
        return jobBuilderFactory.get("deleteDmRoomAndDmJob")
                .listener(new JobLoggerListener())
                .start(deleteDmList())
                .next(deleteDmRoom())
                .build();
    }

    @Bean
    public Step deleteDmList() {
        return stepBuilderFactory.get("deleteDmList")
                .allowStartIfComplete(true)
                .<DmRoom, List<Dm>>chunk(CHUNK_SIZE)
                .reader(dmRoomItemReader())
                .processor(dmRoomToDmItemProcessor())
                .writer(dmItemWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<DmRoom> dmRoomItemReader() {
        return new JpaPagingItemReaderBuilder<DmRoom>()
                .name("dmRoomItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(CHUNK_SIZE)
                .queryString("select d from DmRoom d where d.isChefExit = true " +
                        "and d.isMemberExit = true")
                .build();
    }

    @Bean
    public ItemProcessor<DmRoom, List<Dm>> dmRoomToDmItemProcessor() {
        return dmRepository::findByDmroom;
    }

    @Bean
    public ItemWriter<List<Dm>> dmItemWriter() {
        return items -> {
            for (List<Dm> dmList : items) {
                dmRepository.deleteAll(dmList);
            }
        };
    }

    @Bean
    public Step deleteDmRoom() {
        return stepBuilderFactory.get("deleteDmRoom")
                .allowStartIfComplete(true)
                .<DmRoom, DmRoom>chunk(CHUNK_SIZE)
                .reader(dmRoomItemReader())
                .processor(dmRoomToDmRoomProcessor())
                .writer(dmRoomItemWriter())
                .build();
    }

    @Bean
    public ItemProcessor<DmRoom, DmRoom> dmRoomToDmRoomProcessor() {
        return dmRoom -> dmRoom;
    }

    @Bean
    public ItemWriter<DmRoom> dmRoomItemWriter() {
        return dmRoomRepository::deleteAll;
    }
}
