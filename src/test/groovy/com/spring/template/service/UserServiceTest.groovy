package com.spring.template.service

import com.spring.template.repository.UserRepository
import com.spring.template.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

@DataJpaTest
class UserServiceTest extends Specification {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserService userFacade;

    def setup() {
        userFacade = new UserService(userRepository, passwordEncoder);
    }
}
