package com.kgu.bravoHealthPark.domain.user.repository;

import com.kgu.bravoHealthPark.domain.user.domain.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByUsername(String username);

    User findByUserId(Long userId);

    User findByUsername(String username);

    User findByPhoneNumber(String phoneNumber);

    User findByUsernameAndPhoneNumber(String username, String phoneNumber);

    List<User> findAll();

    List<User> findListByUsername(String usernamename);

    List<User> findListByPhoneNumber(String phoneNumber);

}
