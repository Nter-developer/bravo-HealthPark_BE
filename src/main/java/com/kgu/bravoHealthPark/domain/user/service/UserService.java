package com.kgu.bravoHealthPark.domain.user.service;

import com.kgu.bravoHealthPark.domain.user.domain.User;
import com.kgu.bravoHealthPark.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

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

    public List<User> findUserByName(String name) {
        List<User> userList = userRepository.findByName(name);
        return userList;
    }

}
