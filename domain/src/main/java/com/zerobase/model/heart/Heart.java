package com.zerobase.model.heart;


import com.zerobase.model.common.Audit;
import com.zerobase.model.recipe.model.Recipe;
import com.zerobase.model.member.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Heart extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Recipe.class, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;


}
