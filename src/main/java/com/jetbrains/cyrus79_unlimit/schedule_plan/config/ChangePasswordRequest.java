package com.jetbrains.cyrus79_unlimit.schedule_plan.config;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String oldPassword;
    private String newPassword;
}
