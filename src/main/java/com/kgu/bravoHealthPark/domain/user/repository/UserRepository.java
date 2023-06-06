package com.kgu.bravoHealthPark.domain.user.repository;

import com.kgu.bravoHealthPark.domain.user.domain.User;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByLoginId(String loginId);

    User findByUserId(Long userId);

    User findByLoginId(String loginId);

    List<User> findAll();

}
