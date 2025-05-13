package com.jetbrains.cyrus79_unlimit.schedule_plan.controller;

import com.jetbrains.cyrus79_unlimit.schedule_plan.config.JwtUntil;
import com.jetbrains.cyrus79_unlimit.schedule_plan.dto.LoginRequest;
import com.jetbrains.cyrus79_unlimit.schedule_plan.entity.User;
import com.jetbrains.cyrus79_unlimit.schedule_plan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUntil jwtUntil;

    // Register new user
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    // Login user
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        Optional<User> user = userService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        if (user.isPresent()) {
            String token = jwtUntil.generateToken(user.get().getUsername());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("username", user.get().getUsername());
            response.put("message", "Login successful");

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
