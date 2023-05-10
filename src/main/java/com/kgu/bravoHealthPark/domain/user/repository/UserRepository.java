package com.kgu.bravoHealthPark.domain.user.repository;

import com.kgu.bravoHealthPark.domain.user.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesById(String id);

    User findByUserId(Long userId);

    User findById(String id);

    User findByPhoneNumber(String phoneNumber);

    User findByIdAndPhoneNumber(String id, String phoneNumber);

    List<User> findAll();

    List<User> findListById(String id);

    List<User> findListByUsername(String username);


}
