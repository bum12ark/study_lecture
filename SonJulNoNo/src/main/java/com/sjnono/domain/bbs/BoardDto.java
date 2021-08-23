package com.sjnono.domain.bbs;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class BoardDto {
    private Long id;
    private String title;
    private String content;
    private String hits;
}
