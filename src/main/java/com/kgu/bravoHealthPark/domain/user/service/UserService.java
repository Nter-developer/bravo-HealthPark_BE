package com.kgu.bravoHealthPark.domain.user.service;


import com.kgu.bravoHealthPark.config.jwt.JwtTokenProvider;
import com.kgu.bravoHealthPark.config.jwt.SecurityConfig;
import com.kgu.bravoHealthPark.domain.user.domain.User;
import com.kgu.bravoHealthPark.domain.user.dto.LoginForm;
import com.kgu.bravoHealthPark.domain.user.dto.SignUpForm;
import com.kgu.bravoHealthPark.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SecurityConfig securityConfig;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;


    /**
     * 생성
     */
    @Transactional
    public User join(User user) {
        hasDuplicateUser(user); //중복 계정 확인

        userRepository.save(user);
        return user;
    }

    /**
     * 삭제
     */
    @Transactional
    public void delete(User user) {
        userRepository.delete(user);
    }

    /**
     * 검색
     */
    public User findUserById(Long userId) {
        User user = userRepository.findByUserId(userId);
        return user;
    }

    public User findUserByPhoneNumber(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        return user;
    }

    public User findUserByNameAndPhoneNumber(String name, String phoneNumber) {
        User user = userRepository.findByNameAndPhoneNumber(name, phoneNumber);
        return user;
    }

    public User findUserByName(String name) {
        User user = userRepository.findByName(name);
        return user;
    }

    public List<User> findUserListByName(String name) {
        List<User> userList = userRepository.findListByName(name);
        return userList;
    }

    public List<User> findUserListByPhoneNumber(String phoneNumber) {
        List<User> userList = userRepository.findListByName(phoneNumber);
        return userList;
    }

    private void hasDuplicateUser(User user) {
        List<User> findUsers = userRepository.findListByPhoneNumber(user.getPhoneNumber());
        if (!findUsers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 전화번호 입니다");
        }
    }

    @Transactional
    public Long signup(SignUpForm signUpForm) {
        BCryptPasswordEncoder encoder = securityConfig.bCryptPasswordEncoder();
        String encode = encoder.encode(signUpForm.getPhoneNumber());
        User user = userRepository.save(signUpForm.toEntity(encode));
        return user.getUserId();
    }

    @Transactional
    public String login(LoginForm loginForm) {
        User user = userRepository.findByPhoneNumber(loginForm.getPhoneNumber());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getName(), user.getEncPhoneNumber());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return jwtTokenProvider.generateToken(authentication);
    }

}
