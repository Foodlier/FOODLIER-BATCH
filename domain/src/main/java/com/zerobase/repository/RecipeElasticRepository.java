package com.zerobase.repository;

import com.zerobase.model.recipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeElasticRepository extends JpaRepository<Recipe, Long> {
}
