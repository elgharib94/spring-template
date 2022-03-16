package com.spring.template.domain.dto;

import com.spring.template.domain.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserDTO {
    private UUID id;

    @NotEmpty(message = "username is required")
    @Size(min = 4, max = 32, message = "username must be between 4 and 32 characters long")
    private String username;

    @NotEmpty(message = "authorities is required")
    private Set<Role> authorities;
}