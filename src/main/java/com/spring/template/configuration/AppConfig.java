package com.spring.template.configuration;

import com.spring.template.domain.dto.RegisterUserDTO;
import com.spring.template.domain.model.Role;
import com.spring.template.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class AppConfig {

    @Bean
    CommandLineRunner run(UserService userFacade) {
        return args -> {
            userFacade.create(new RegisterUserDTO("test_admin", "1234", Set.of(Role.ADMIN)));
            userFacade.create(new RegisterUserDTO("test_manager", "1234", Set.of(Role.MANAGER)));
            userFacade.create(new RegisterUserDTO("test_user1", "1234", Set.of(Role.USER)));
            userFacade.create(new RegisterUserDTO("test_user2", "1234", Set.of(Role.USER)));
            userFacade.create(new RegisterUserDTO("test_user3", "1234", Set.of(Role.USER)));
            userFacade.create(new RegisterUserDTO("test_user4", "1234", Set.of(Role.USER)));
            userFacade.create(new RegisterUserDTO("test_user5", "1234", Set.of(Role.USER)));
            userFacade.create(new RegisterUserDTO("test_user6", "1234", Set.of(Role.USER)));
        };
    }
}
