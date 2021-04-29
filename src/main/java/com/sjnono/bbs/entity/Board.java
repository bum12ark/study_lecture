package com.sjnono.bbs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter @NoArgsConstructor
@Entity
public class Board {
    @Id @GeneratedValue
    private Long id;
    private String articleTitle;
    private String articleDetail;
    private String articleHits;
    private String articleRecommend;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn()
    private UserInfo userInfo;
}
