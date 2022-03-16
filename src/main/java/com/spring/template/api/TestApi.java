package com.spring.template.api;


import com.spring.template.authorization.RequiresAdminRole;
import com.spring.template.authorization.RequiresManagerRole;
import com.spring.template.authorization.RequiresUserRole;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
@RequestMapping("api/test")
@RequiredArgsConstructor
@Tag(name = "Test")
public class TestApi {

    @RequiresAdminRole
    @GetMapping("admin")
    public String admin(Principal principal) {
        return "I am admin";
    }

    @RequiresManagerRole
    @GetMapping("manager")
    public String manager() {
        return "I am manager";
    }

    @RequiresUserRole
    @GetMapping("user")
    public String user() {
        return "I am user";
    }
}
