package com.zerobase.model.comment.comment.domain.model;


import com.zerobase.model.common.Audit;
import com.zerobase.model.recipe.model.Recipe;
import com.zerobase.model.member.Member;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "comment")
public class Comment extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Column(nullable = false)
    private String message;

    @Builder.Default
    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean isDeleted = false;
}
