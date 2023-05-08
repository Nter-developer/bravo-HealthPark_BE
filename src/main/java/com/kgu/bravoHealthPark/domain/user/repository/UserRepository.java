package com.kgu.bravoHealthPark.domain.user.repository;

import com.kgu.bravoHealthPark.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserId(Long userId);
    User findByName(String name);
    User findByPhoneNumber(String phoneNumber);

    User findByNameAndPhoneNumber(String name, String phoneNumber);
    List<User> findAll();
    List<User> findListByName(String name);

    List<User> findListByPhoneNumber(String phoneNumber);

}
