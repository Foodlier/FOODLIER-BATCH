package com.zerobase.model.member;

import com.zerobase.model.chefmember.ChefMember;
import com.zerobase.model.common.Audit;
import com.zerobase.model.member.type.RegistrationType;
import com.zerobase.model.member.vo.Address;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "member")
public class Member extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private ChefMember chefMember;
    @Column(unique = true, nullable = false)
    private String nickname;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(unique = true, nullable = false)
    private String phoneNumber;
    private String profileUrl;
    @Embedded
    private Address address;
    private long point;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RegistrationType registrationType;
    @Builder.Default
    private boolean isDeleted = false;
    @ElementCollection
    @Builder.Default
    private List<String> roles = new ArrayList<>();

}
