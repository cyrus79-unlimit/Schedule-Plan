package com.jetbrains.cyrus79_unlimit.schedule_plan.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String repeatPassword;
    private String name;
    private String email;
    private LocalDate birthday;
}
