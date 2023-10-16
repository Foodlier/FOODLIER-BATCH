package com.zerobase.model.member.vo;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    private String roadAddress;
    private String addressDetail;
    private double lat;
    private double lnt;
}
