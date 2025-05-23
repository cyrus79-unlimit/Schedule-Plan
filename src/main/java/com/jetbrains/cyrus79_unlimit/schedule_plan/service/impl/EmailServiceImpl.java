package com.jetbrains.cyrus79_unlimit.schedule_plan.service.impl;

import com.jetbrains.cyrus79_unlimit.schedule_plan.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private  final JavaMailSender mailSender;

    @Override
    public void sentOtp(String toEmail, String otpCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Verify your email");
        message.setText("Your OTP code is: " + otpCode);
        mailSender.send(message);
    }
}
