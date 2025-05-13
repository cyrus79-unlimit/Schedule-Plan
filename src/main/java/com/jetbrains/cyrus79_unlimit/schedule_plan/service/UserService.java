package com.jetbrains.cyrus79_unlimit.schedule_plan.service;

import com.jetbrains.cyrus79_unlimit.schedule_plan.config.CustomUserDetails;
import com.jetbrains.cyrus79_unlimit.schedule_plan.dto.UpdateUserRequest;
import com.jetbrains.cyrus79_unlimit.schedule_plan.entity.User;
import com.jetbrains.cyrus79_unlimit.schedule_plan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Authenticate user with username and password
    public Optional<User> authenticate(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    // Update user info (name, birthday, etc.)
//    public User updateUserInfo(Long id, User updatedUser) {
//        return userRepository.findById(id).map(user -> {
//            user.setName(updatedUser.getName());
//            user.setBirthday(updatedUser.getBirthday());
//            user.setEmail(updatedUser.getEmail());
//
//            if (updatedUser.getUsername() != null && !updatedUser.getUsername().isBlank()) {
//                user.setUsername(updatedUser.getUsername());
//            }
//
//            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
//                user.setPassword(passwordEncoder().encode(updatedUser.getPassword()));
//            }
//
//            return userRepository.save(user);
//        }).orElseThrow(() -> new RuntimeException("User not found"));
//    }

    //Update current user
    public User updateCurrentUser(String username, UpdateUserRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update only allowed fields
//        user.setName(updatedData.getName());
//        user.setBirthday(updatedData.getBirthday());
//        user.setEmail(updatedData.getEmail());
//
//        if (updatedData.getUsername() != null && !updatedData.getUsername().isBlank()) {
//            user.setUsername(updatedData.getUsername());
//        }
//
//        if (updatedData.getPassword() != null && !updatedData.getPassword().isBlank()) {
//            user.setPassword(passwordEncoder().encode(updatedData.getPassword()));
//        }
        if (request.getName() != null) user.setName(request.getName());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getBirthday() != null) user.setBirthday(request.getBirthday());
        return userRepository.save(user);
    }

    // Change password
    public boolean changePassword(Long id, String oldPassword, String newPassword) {
        return userRepository.findById(id).map(user -> {
            if (user.getPassword().equals(oldPassword)) {
                user.setPassword(newPassword);
                userRepository.save(user);
                return true;
            }
            return false;
        }).orElse(false);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }
}
