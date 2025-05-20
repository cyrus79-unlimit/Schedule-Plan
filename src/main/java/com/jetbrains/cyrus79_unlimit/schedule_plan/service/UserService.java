package com.jetbrains.cyrus79_unlimit.schedule_plan.service;

import com.jetbrains.cyrus79_unlimit.schedule_plan.dto.request.RegisterRequest;
import com.jetbrains.cyrus79_unlimit.schedule_plan.dto.request.UpdateUserRequest;
import com.jetbrains.cyrus79_unlimit.schedule_plan.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface UserService {
    Optional<User> authenticate(String username, String password);

    User registerUser(RegisterRequest registerRequest);

    Optional<User> getUserById(Long id);

    User findByUsername(String username);

    void deleteUser(Long id);

    User updateCurrentUser(String username, UpdateUserRequest request);

    boolean changePassword(Long id, String oldPassword, String newPassword);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
