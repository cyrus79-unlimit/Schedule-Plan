package com.jetbrains.cyrus79_unlimit.schedule_plan.controller;

import com.jetbrains.cyrus79_unlimit.schedule_plan.dto.request.VerifyOtpRequest;
import com.jetbrains.cyrus79_unlimit.schedule_plan.security.JwtUtil;
import com.jetbrains.cyrus79_unlimit.schedule_plan.dto.request.LoginRequest;
import com.jetbrains.cyrus79_unlimit.schedule_plan.dto.request.RegisterRequest;
import com.jetbrains.cyrus79_unlimit.schedule_plan.entity.User;
import com.jetbrains.cyrus79_unlimit.schedule_plan.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    private final JwtUtil jwtUtil;

    // Register with email and password (sent otp to mail)
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok("OTP has been sent to your email. Please verify to activate your account.");
    }

    // Verify OTP to complete registration
    @PostMapping("/verify")
    public ResponseEntity<String> verifyOtp(@RequestBody VerifyOtpRequest request) {
        boolean verified = userService.verifyOtp(request.getEmail(), request.getOtpCode(), request.getPassword());
        if (verified) {
            return ResponseEntity.ok("Email verified successfully. You can now login.");
        } else {
            return ResponseEntity.badRequest().body("Invalid OTP or email.");
        }
    }

    // Login user
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOtp = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        if (userOtp.isPresent()) {
            User user = userOtp.get();

            if (!user.isEnabled()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Email not verified. Please check your email for the OTP.");
            }

            String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("email", user.getEmail());
            response.put("role", user.getRole());
            response.put("message", "Login successful");

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
