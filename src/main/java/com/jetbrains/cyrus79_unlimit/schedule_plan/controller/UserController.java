package com.jetbrains.cyrus79_unlimit.schedule_plan.controller;

import com.jetbrains.cyrus79_unlimit.schedule_plan.config.ChangePasswordRequest;
import com.jetbrains.cyrus79_unlimit.schedule_plan.config.CustomUserDetails;
import com.jetbrains.cyrus79_unlimit.schedule_plan.config.JwtUntil;
import com.jetbrains.cyrus79_unlimit.schedule_plan.dto.UpdateUserRequest;
import com.jetbrains.cyrus79_unlimit.schedule_plan.entity.User;
import com.jetbrains.cyrus79_unlimit.schedule_plan.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUntil jwtUntil;

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update user info (name, birthday, etc.)
//    @PutMapping("/{id}")
//    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
//        return ResponseEntity.ok(userService.updateUserInfo(id, updatedUser));
//    }

    //Update user info (token extract data)
    @PutMapping("update")
    public ResponseEntity<User> updateUser(@RequestBody UpdateUserRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        User currentUser = userService.updateCurrentUser(username, updatedData);
        return ResponseEntity.ok(userService.updateCurrentUser(username,request));
    }

    // Change password
    @PutMapping("/{id}/password")
    public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody ChangePasswordRequest request) {
        boolean success = userService.changePassword(id, request.getOldPassword(), request.getNewPassword());
        if (success) {
            return ResponseEntity.ok("Password updated");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect old password");
        }
    }
}
