package com.sjnono.domain.bbs;

import com.sjnono.domain.user.UserInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter @NoArgsConstructor
@Entity
public class Board {
    @Id @GeneratedValue
    private Long id;
    private String title;
    private String content;
    private String hits;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn()
    private UserInfo userInfo;
}
