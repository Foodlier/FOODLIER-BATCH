package com.zerobase.model.recipe.model;


import com.zerobase.model.common.Audit;
import com.zerobase.model.recipe.vo.RecipeIngredient;
import com.zerobase.model.comment.comment.domain.model.Comment;
import com.zerobase.model.heart.Heart;
import com.zerobase.model.member.Member;
import com.zerobase.model.recipe.type.Difficulty;
import com.zerobase.model.recipe.vo.RecipeDetail;
import com.zerobase.model.recipe.vo.RecipeStatistics;
import com.zerobase.model.recipe.vo.Summary;
import com.zerobase.model.review.recipe.domain.model.RecipeReview;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Recipe extends Audit {
    private final static int ZERO = 0;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Summary summary;

    private String mainImageUrl;

    private int expectedTime;

    private int heartCount;
    private int commentCount;
    @Embedded
    private RecipeStatistics recipeStatistics;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    private Boolean isPublic;

    @Builder.Default
    private Boolean isQuotation = false;

    @Builder.Default
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
    @Builder.Default
    private List<RecipeReview> recipeReviewList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Heart> heartList = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "recipe_detail")
    @Builder.Default
    private List<RecipeDetail> recipeDetailList = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "recipe_ingredient")
    @Builder.Default
    private List<RecipeIngredient> recipeIngredientList = new ArrayList<>();

}
