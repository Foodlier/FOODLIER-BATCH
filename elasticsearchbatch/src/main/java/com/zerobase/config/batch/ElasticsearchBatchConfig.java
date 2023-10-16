package com.zerobase.config.batch;

import com.zerobase.listener.JobLoggerListener;
import com.zerobase.model.recipe.document.RecipeDocument;
import com.zerobase.model.recipe.model.Recipe;
import com.zerobase.model.recipe.vo.RecipeIngredient;
import com.zerobase.repository.RecipeElasticRepository;
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
import java.util.stream.Collectors;

@Configuration
@EnableBatchProcessing
@Slf4j
@RequiredArgsConstructor
public class ElasticsearchBatchConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final RecipeElasticRepository recipeElasticRepository;

    private static final String DELIMITER = " ";
    private static final int CHUNK_SIZE = 1000;

    @Bean
    public Job changeWriterJob() {
        return jobBuilderFactory.get("changeWriterJob")
                .listener(new JobLoggerListener())
                .start(updateNickName())
                .build();
    }

    @Bean
    public Step updateNickName() {
        return stepBuilderFactory.get("updateNickName")
                .allowStartIfComplete(true)
                .<Recipe, RecipeDocument>chunk(CHUNK_SIZE)
                .reader(recipeItemReader())
                .processor(recipeToRecipeDocumentItemProcessor())
                .writer(recipeDocumentItemWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<Recipe> recipeItemReader() {
        return new JpaPagingItemReaderBuilder<Recipe>()
                .name("recipeItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(CHUNK_SIZE)
                .queryString("select r from Recipe r")
                .build();
    }

    @Bean
    public ItemProcessor<Recipe, RecipeDocument> recipeToRecipeDocumentItemProcessor() {
        return recipe -> RecipeDocument.builder()
                .id(recipe.getId())
                .memberId(recipe.getMember().getId())
                .title(recipe.getSummary().getTitle())
                .writer(recipe.getMember().getNickname())
                .ingredients(recipe.getRecipeIngredientList().stream()
                        .map(RecipeIngredient::getName)
                        .collect(Collectors.joining(DELIMITER)))
                .numberOfHeart(recipe.getHeartList().size())
                .numberOfComment(recipe.getCommentList().size())
                .build();
    }

    @Bean
    public ItemWriter<RecipeDocument> recipeDocumentItemWriter() {
        return new ItemWriter<RecipeDocument>() {
            @Override
            public void write(List<? extends RecipeDocument> items) throws Exception {
                recipeElasticRepository.saveAll(items);
            }
        };
    }
}
