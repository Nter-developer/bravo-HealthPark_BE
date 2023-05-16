package com.kgu.bravoHealthPark.domain.user.service;

import com.kgu.bravoHealthPark.domain.user.domain.User;
import com.kgu.bravoHealthPark.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User findByUser(Long userId){
        return userRepository.findByUserId(userId);
    }
}
