package com.spring.template.authorization;


import com.spring.template.domain.model.Role;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@PreAuthorize("hasAnyRole('" + Role.Code.ADMIN + "','" + Role.Code.MANAGER + "')")
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresAdminOrManagerRole {
}