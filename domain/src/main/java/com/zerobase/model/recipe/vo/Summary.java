package com.zerobase.model.recipe.vo;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Summary {

    private String title;
    private String content;

}
