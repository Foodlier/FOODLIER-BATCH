package com.zerobase.repository;

import com.zerobase.model.recipe.document.RecipeDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface RecipeElasticRepository extends ElasticsearchRepository<RecipeDocument, Long> {
}
