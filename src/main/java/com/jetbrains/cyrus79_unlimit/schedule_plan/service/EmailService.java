package com.jetbrains.cyrus79_unlimit.schedule_plan.service;

public interface EmailService {
    void sentOtp(String toEmail, String otpCode);
}
