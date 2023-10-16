package com.zerobase.model.review.chef.domain.model;


import com.zerobase.model.common.Audit;
import com.zerobase.model.chefmember.ChefMember;
import com.zerobase.model.request.Request;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "chef_review")
public class ChefReview extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private int star;

    @ManyToOne
    @JoinColumn(name = "chef_member_id")
    private ChefMember chefMember;

    @OneToOne
    private Request request;
}
