package com.jetbrains.cyrus79_unlimit.schedule_plan.dto.request;

import lombok.Data;

@Data
public class VerifyOtpRequest {
    private String email;
    private String otpCode;
    private String password;
}
