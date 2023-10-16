package com.zerobase.model.chefmember;

import com.zerobase.model.chefmember.type.GradeType;
import com.zerobase.model.common.Audit;
import com.zerobase.model.member.Member;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "chef_member")
public class ChefMember extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Member member;
    private long exp;
    @Enumerated(EnumType.STRING)
    private GradeType gradeType;
    private String introduce;
    private double starAvg;
    private int starSum;
    private int reviewCount;

}
