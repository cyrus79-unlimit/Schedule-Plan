package com.jetbrains.cyrus79_unlimit.schedule_plan.dto.response;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String email;
    private LocalDate birthday;
    private String role;
}
