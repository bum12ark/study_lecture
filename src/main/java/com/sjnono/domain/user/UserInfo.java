package com.sjnono.domain.user;

import com.sjnono.domain.bbs.Board;
import lombok.*;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class UserInfo {
    @Id @GeneratedValue
    private Long id;
    private String email;
    private String name;
    private String password;

    @OneToMany(mappedBy = "userInfo")
    private List<Board> boardList = new ArrayList<>();

    @Builder
    public UserInfo(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
