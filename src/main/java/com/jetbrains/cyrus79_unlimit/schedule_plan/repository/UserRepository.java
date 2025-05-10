package com.jetbrains.cyrus79_unlimit.schedule_plan.repository;

import com.jetbrains.cyrus79_unlimit.schedule_plan.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findByUsernameAndPassword(String username, String password);
}
