package com.kgu.bravoHealthPark.domain.user.repository;

import com.kgu.bravoHealthPark.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUserId(Long userId);
}
