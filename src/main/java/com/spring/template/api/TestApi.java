package com.spring.template.api;


import com.spring.template.authorization.RequiresAdminRole;
import com.spring.template.authorization.RequiresManagerRole;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/test")
@RequiredArgsConstructor
@Tag(name = "Test")
public class TestApi {

    @RequiresAdminRole
    @GetMapping("admin")
    public String admin(Authentication authentication) {

        return "I am admin";
    }

    @RequiresManagerRole
    @GetMapping("manager")
    public String manager() {
        return "I am manager";
    }

    @GetMapping("user")
    public String user() {
        return "I am user";
    }
}
