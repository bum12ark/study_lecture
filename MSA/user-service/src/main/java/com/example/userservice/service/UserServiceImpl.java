package com.example.userservice.service;

import com.example.userservice.client.OrderServiceClient;
import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseOrder;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OrderServiceClient orderServiceClient;

    private final BCryptPasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;
    private final Environment environment;

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
    public UserDto getUserByUserId(String userId) {
        User findUser = userRepository.findByUserId(userId).orElseThrow(NullPointerException::new);

        /* Using as rest template
        String orderUrl = String.format(environment.getProperty("order-service.url.getorders"), userId);
        ResponseEntity<List<ResponseOrder>> responseEntity =
                restTemplate.exchange(orderUrl, HttpMethod.GET,
                        null, new ParameterizedTypeReference<List<ResponseOrder>>() {});
        List<ResponseOrder> orders = responseEntity.getBody();
        */


        /* Using ErrorDecoder feign client
        List<ResponseOrder> orders = orderServiceClient.getOrdersWrongAddress(userId);
         */

        /* Using feign client */
        List<ResponseOrder> orders = orderServiceClient.getOrders(userId);

        return new UserDto(findUser.getEmail(), findUser.getName(),
                findUser.getUserId(), findUser.getEncryptedPwd(), orders);
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

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(NullPointerException::new);
        return new UserDto(user.getEmail(), user.getName(), user.getUserId(), user.getEncryptedPwd());
    }
}
