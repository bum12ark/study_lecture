package com.sjnono.domain.user;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class UserInfoDto {
    private Long id;
    private String email;
    private String name;
    private String password;
}
