package com.jetbrains.cyrus79_unlimit.schedule_plan.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public class UpdateUserRequest {
    @Schema
    private String name;
    @Schema
    private String email;
    @Schema
    private LocalDate birthday;

    public UpdateUserRequest(String name, String email, LocalDate birthday) {
        this.name = name;
        this.email = email;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
