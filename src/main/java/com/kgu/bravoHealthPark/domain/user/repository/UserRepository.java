package com.kgu.bravoHealthPark.domain.user.repository;

import com.kgu.bravoHealthPark.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserId(Long userId);
    User findByPhoneNumber(String phoneNumber);

    List<User> findAll();
    List<User> findByName(String name);


}
