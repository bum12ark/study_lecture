package com.sjnono.bbs.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@Entity
public class UserInfo {
    @Id @GeneratedValue
    private Long id;
    private String email;
    private String name;
    private String password;

    @OneToMany(mappedBy = "userInfo")
    private List<Board> boardList = new ArrayList<>();
}
