package com.spring.template.domain.dto;

import com.spring.template.domain.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdatePasswordDTO {

    @NotEmpty(message = "password is required")
    @Size(min = 4, max = 32, message = "password must be between 4 and 32 characters long")
    private String oldPassword;

    @NotEmpty(message = "password is required")
    @Size(min = 4, max = 32, message = "password must be between 4 and 32 characters long")
    private String newPassword;
}