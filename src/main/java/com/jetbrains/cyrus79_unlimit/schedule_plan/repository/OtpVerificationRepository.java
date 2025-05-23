package com.jetbrains.cyrus79_unlimit.schedule_plan.repository;

import com.jetbrains.cyrus79_unlimit.schedule_plan.entity.OtpVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpVerificationRepository extends JpaRepository<OtpVerification, Long> {
    Optional<OtpVerification> findByEmailAndOtpCode(String email, String otpCode);
}
