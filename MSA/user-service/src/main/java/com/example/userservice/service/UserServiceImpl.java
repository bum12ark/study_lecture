package com.example.userservice.service;

import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User createUser(RequestUser requestUser) {
        String userId = UUID.randomUUID().toString();

        User user = User.builder()
                .userId(userId)
                .email(requestUser.getEmail())
                .name(requestUser.getName())
                .encryptedPwd(passwordEncoder.encode(requestUser.getPwd()))
                .build();

        return userRepository.save(user);
    }

    @Override
    public User getUserByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(NullPointerException::new);
    }

    @Override
    public Iterable<User> getUserByAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User findUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        return new org.springframework.security.core.userdetails.User(
                findUser.getEmail(), findUser.getEncryptedPwd(),
                true, true, true, true,
                new ArrayList<>()
        );
    }
}
