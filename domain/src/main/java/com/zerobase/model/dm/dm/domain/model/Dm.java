package com.zerobase.model.dm.dm.domain.model;


import com.zerobase.model.common.Audit;
import com.zerobase.model.dm.dm.type.MessageType;
import com.zerobase.model.dm.room.domain.model.DmRoom;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "dm")
public class Dm extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private DmRoom dmroom;
    @Column(nullable = false)
    private String text;
    @Column(nullable = false)
    private String flag;
    @Enumerated(EnumType.STRING)
    private MessageType messageType;
}
