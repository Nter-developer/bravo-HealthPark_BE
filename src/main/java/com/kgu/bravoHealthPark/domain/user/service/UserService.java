package com.kgu.bravoHealthPark.domain.user.service;


import com.kgu.bravoHealthPark.domain.user.domain.User;
import com.kgu.bravoHealthPark.domain.user.domain.Authority;
import com.kgu.bravoHealthPark.domain.user.jwt.dto.UserDto;
import com.kgu.bravoHealthPark.domain.user.jwt.exception.DuplicateMemberException;
import com.kgu.bravoHealthPark.domain.user.jwt.exception.NotFoundMemberException;
import com.kgu.bravoHealthPark.domain.user.jwt.util.SecurityUtil;
import com.kgu.bravoHealthPark.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    /*

     */

    /**
     * 생성
     */

    @Transactional
    public User join(User user) {
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
        User user = userRepository.findByUsernameAndPhoneNumber(name, phoneNumber);
        return user;
    }

    public User findUserByName(String name) {
        User user = userRepository.findByUsername(name);
        return user;
    }

    public List<User> findUserListByName(String name) {
        List<User> userList = userRepository.findListByUsername(name);
        return userList;
    }

    public List<User> findUserListByPhoneNumber(String phoneNumber) {
        List<User> userList = userRepository.findListByUsername(phoneNumber);
        return userList;
    }

    public String encdoing(String phoneNumber) {
        return passwordEncoder.encode(phoneNumber);
    }


    public boolean matches(CharSequence password, String encodedPassword) {
        System.out.println(passwordEncoder.matches(password, encodedPassword));
        return passwordEncoder.matches(password, encodedPassword);
    }

    @Transactional
    public UserDto signup(UserDto userDto) {

        if (userDto.getUsername().equals("admin")) {
            Authority authority = Authority.builder()
                    .authorityName("ROLE_ADMIN")
                    .build();

            User user = User.builder()
                    .username(userDto.getUsername())
                    .phoneNumber(passwordEncoder.encode(userDto.getPhoneNumber()))
                    .authorities(Collections.singleton(authority))
                    .activated(true)
                    .build();

            return UserDto.from(userRepository.save(user));
        } else {

            Authority authority = Authority.builder()
                    .authorityName("ROLE_USER")
                    .build();

            User user = User.builder()
                    .username(userDto.getUsername())
                    .phoneNumber(passwordEncoder.encode(userDto.getPhoneNumber()))
                    .authorities(Collections.singleton(authority))
                    .activated(true)
                    .build();

            return UserDto.from(userRepository.save(user));
        }
    }

    @Transactional(readOnly = true)
    public UserDto getUserWithAuthorities(String username) {
        return UserDto.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
    }

    @Transactional(readOnly = true)
    public UserDto getMyUserWithAuthorities() {
        return UserDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                        .orElseThrow(() -> new NotFoundMemberException("Member not found"))
        );
    }

}
