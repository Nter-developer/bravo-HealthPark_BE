package com.kgu.bravoHealthPark.domain.user.service;


import com.kgu.bravoHealthPark.domain.user.domain.User;
import com.kgu.bravoHealthPark.domain.user.domain.Authority;
import com.kgu.bravoHealthPark.domain.user.dto.UserDto;
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

    public List<User> findUserByUsername(String username) {
        List<User> userList = userRepository.findListByUsername(username);
        return userList;
    }

    public User findUserByPhoneNumber(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        return user;
    }

    public User findUserByIdAndPhoneNumber(String id, String phoneNumber) {
        User user = userRepository.findByIdAndPhoneNumber(id, phoneNumber);
        return user;
    }

    public User findUserById(String id) {
        User user = userRepository.findById(id);
        return user;
    }


    public List<User> findUserListById(String id) {
        List<User> userList = userRepository.findListById(id);
        return userList;
    }

    public List<User> findUserListByPhoneNumber(String phoneNumber) {
        List<User> userList = userRepository.findListById(phoneNumber);
        return userList;
    }


//    public boolean matches(CharSequence password, String encodedPassword) {
//        System.out.println(passwordEncoder.matches(password, encodedPassword));
//        return passwordEncoder.matches(password, encodedPassword);
//    }

    @Transactional
    public UserDto signup(UserDto userDto) {

        if (userDto.getId().equals("admin")) {
            Authority authority = Authority.builder()
                    .authorityName("ROLE_ADMIN")
                    .build();

            User user = User.builder()
                    .id(userDto.getId())
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
                    .id(userDto.getId())
                    .username(userDto.getUsername())
                    .phoneNumber(passwordEncoder.encode(userDto.getPhoneNumber()))
                    .authorities(Collections.singleton(authority))
                    .activated(true)
                    .build();

            return UserDto.from(userRepository.save(user));
        }
    }

    @Transactional(readOnly = true)
    public UserDto getUserWithAuthorities(String id) {
        return UserDto.from(userRepository.findOneWithAuthoritiesById(id).orElse(null));
    }

    @Transactional(readOnly = true)
    public UserDto getMyUserWithAuthorities() {
        return UserDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findOneWithAuthoritiesById)
                        .orElseThrow(() -> new NotFoundMemberException("Member not found"))
        );
    }

}
