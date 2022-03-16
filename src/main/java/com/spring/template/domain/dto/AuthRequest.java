package com.spring.template.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class AuthRequest {

    @NotEmpty(message = "username is required")
    @Size(min = 4, max = 32, message = "username must be between 4 and 32 characters long")
    private String username;

    @NotEmpty(message = "password is required")
    @Size(min = 4, max = 32, message = "password must be between 4 and 32 characters long")
    private String password;
}