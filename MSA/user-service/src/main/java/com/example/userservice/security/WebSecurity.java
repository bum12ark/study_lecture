package com.example.userservice.security;

import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Environment environment;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        String localIpAddress = "192.168.0.124";
        http.authorizeRequests().antMatchers("/**")
                        .hasIpAddress(localIpAddress)
                        .and()
                        .addFilter(getAuthenticationFilter());


        http.headers().frameOptions().disable();
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(userService, environment);
        authenticationFilter.setAuthenticationManager(authenticationManager());

        return authenticationFilter;
    }

    // SELECT pwd FROM users WHERE email = ?
    // db_pwd(encrypted) == input_pwd(encrypted)
    // 패스워드를 비교할 때 사용할 인코딩 방식 설정
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // SELECT pwd FROM users WHERE email = ?
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }
}
