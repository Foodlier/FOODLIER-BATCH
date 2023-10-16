package com.zerobase.model.request;

import com.zerobase.model.chefmember.ChefMember;
import com.zerobase.model.common.Audit;
import com.zerobase.model.recipe.model.Recipe;
import com.zerobase.model.dm.room.domain.model.DmRoom;
import com.zerobase.model.member.Member;
import com.zerobase.model.request.vo.Ingredient;
import com.zerobase.model.review.chef.domain.model.ChefReview;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cook_request")
public class Request extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private Long expectedPrice;
    @Column(nullable = false)
    private LocalDateTime expectedAt;
    @Column(nullable = false)
    private boolean isPaid;
    private Long paidPrice;
    @ElementCollection
    @CollectionTable(name = "ingredient")
    @Builder.Default
    private List<Ingredient> ingredientList = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "chef_member_id")
    private ChefMember chefMember;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne
    private DmRoom dmRoom;

    @OneToOne
    private ChefReview chefReview;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Column(nullable = false)
    private boolean isFinished;
}
