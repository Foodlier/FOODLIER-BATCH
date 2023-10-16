package com.zerobase.model.comment.reply.domain.model;


import com.zerobase.model.comment.comment.domain.model.Comment;
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
@Table(name = "reply")
public class Reply extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
    @Column(nullable = false)
    private String message;

}
