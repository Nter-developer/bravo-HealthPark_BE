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

    /**
     * 회원가입
     */
    @Transactional
    public UserDto signup(UserDto userDto) {

        if (userDto.getLoginId().equals("admin")) {
            Authority authority = Authority.builder()
                    .authorityName("ROLE_ADMIN")
                    .build();

            User user = User.builder()
                    .loginId(userDto.getLoginId())
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
                    .loginId(userDto.getLoginId())
                    .username(userDto.getUsername())
                    .phoneNumber(passwordEncoder.encode(userDto.getPhoneNumber()))
                    .authorities(Collections.singleton(authority))
                    .activated(true)
                    .build();

            return UserDto.from(userRepository.save(user));
        }
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
        return userRepository.findByUserId(userId);
    }

    public List<User> findUserAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public UserDto getUserWithAuthorities(String id) {
        return UserDto.from(userRepository.findOneWithAuthoritiesByLoginId(id).orElse(null));
    }

    @Transactional(readOnly = true)
    public UserDto getMyUserWithAuthorities() {
        return UserDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findOneWithAuthoritiesByLoginId)
                        .orElseThrow(() -> new NotFoundMemberException("Member not found"))
        );
    }

}
