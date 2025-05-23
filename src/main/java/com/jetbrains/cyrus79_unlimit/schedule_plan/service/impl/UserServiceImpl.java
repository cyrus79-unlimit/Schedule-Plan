package com.jetbrains.cyrus79_unlimit.schedule_plan.service.impl;

import com.jetbrains.cyrus79_unlimit.schedule_plan.config.PasswordEncoderConfig;
import com.jetbrains.cyrus79_unlimit.schedule_plan.dto.request.RegisterRequest;
import com.jetbrains.cyrus79_unlimit.schedule_plan.dto.request.UpdateUserRequest;
import com.jetbrains.cyrus79_unlimit.schedule_plan.entity.OtpVerification;
import com.jetbrains.cyrus79_unlimit.schedule_plan.entity.User;
import com.jetbrains.cyrus79_unlimit.schedule_plan.exception.BadRequestException;
import com.jetbrains.cyrus79_unlimit.schedule_plan.repository.OtpVerificationRepository;
import com.jetbrains.cyrus79_unlimit.schedule_plan.repository.UserRepository;
import com.jetbrains.cyrus79_unlimit.schedule_plan.security.CustomUserDetails;
import com.jetbrains.cyrus79_unlimit.schedule_plan.service.EmailService;
import com.jetbrains.cyrus79_unlimit.schedule_plan.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoderConfig passwordEncoder;

    private final EmailService emailService;

    private final OtpVerificationRepository otpRepository;

    // Authenticate user with email and password
//    @Override
//    public Optional<User> authenticate(String email, String password) {
//        Optional<User> userOpt = userRepository.findByEmail(email);
//        if (userOpt.isPresent()) {
//            User user = userOpt.get();
//            if (passwordEncoder.matches(password, user.getPassword())) {
//                return Optional.of(user);
//            }
//        }
//        return Optional.empty();
//    }

    public Optional<User> authenticate(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String storedPassword = user.getPassword();
            boolean passwordMatches = passwordEncoder.passwordEncoder().matches(password, storedPassword);

            // Log for debugging
            System.out.println("Email: " + email);
            System.out.println("Raw Password: " + password);
            System.out.println("Stored Password: " + storedPassword);
            System.out.println("Password Matches: " + passwordMatches);

            if (passwordMatches) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }



    @Override
    public void registerUser(RegisterRequest registerRequest) {
        if (!registerRequest.getPassword().equals(registerRequest.getRepeatPassword())) {
            throw new BadRequestException("Password do not match.");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BadRequestException("Email already registered");
        }

        String otp = String.valueOf(new Random().nextInt(999999));
        OtpVerification otpRecord = new OtpVerification();
        otpRecord.setEmail(registerRequest.getEmail());
        otpRecord.setOtpCode(otp);
        otpRecord.setCreatedAt(LocalDateTime.now());
        otpRecord.setVerified(false);
        otpRepository.save(otpRecord);

        emailService.sentOtp(registerRequest.getEmail(), otp);
        
    }

    @Override
    public boolean verifyOtp(String email, String otpCode, String password) {
        OtpVerification otp = otpRepository.findByEmailAndOtpCode(email, otpCode)
                .orElseThrow(() -> new BadRequestException("Invalid OTP"));

        if (otp.isVerified()) {
            throw new BadRequestException("Email already verified");
        }

        otp.setVerified(true);
        otpRepository.save(otp);

        // Register the user now (minimal info, can update later)
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.passwordEncoder().encode(password)); // can be stored in OTP or passed later
        user.setName(email.substring(0,email.indexOf("@")));
        user.setBirthday(null);
        user.setEnabled(true);
        user.setRole("USER");
        userRepository.save(user);

        return true;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    //Update current user
    @Override
    public User updateCurrentUser(String email, UpdateUserRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (request.getName() != null) user.setName(request.getName());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getBirthday() != null) user.setBirthday(request.getBirthday());
        return userRepository.save(user);
    }

    // Change password
    @Override
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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
         User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found With Email: " + email));
         return new CustomUserDetails(user);
    }
}
