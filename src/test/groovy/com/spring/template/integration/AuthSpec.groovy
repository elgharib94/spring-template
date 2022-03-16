package com.spring.template.integration

import com.spring.template.ApplicationRunner
import com.spring.template.domain.dto.AuthRequest
import com.spring.template.domain.dto.RegisterUserDTO
import com.spring.template.domain.dto.UserDTO
import com.spring.template.domain.exceptions.ErrorResponse
import com.spring.template.domain.model.Role
import com.spring.template.integration.util.AuthUtil
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ActiveProfiles(["test"])
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ApplicationRunner)
class AuthSpec extends Specification {

    @Autowired
    private AuthUtil authUtil;

    def "User can login successfully with valid username and password"() {
        given:
        String username = "username";
        String password = "password";
        AuthRequest authRequest = new AuthRequest(username, password);

        when:
        authUtil.register(new RegisterUserDTO(username, password, Set.of(Role.ADMIN)), status().isOk())
        and:
        UserDTO userDTO = authUtil.login(authRequest);

        then:
        userDTO.getUsername() == username
        userDTO.getId() !== null
        noExceptionThrown()
    }

    def "User can't login with invalid username or password"() {
        when:
        AuthRequest authRequest = new AuthRequest(
                RandomStringUtils.randomAlphabetic(30),
                RandomStringUtils.randomAlphabetic(30)
        );

        then:
        authUtil.login(authRequest, status().isUnauthorized()) == null
    }

    def "User can register successfully with valid info"() {
        given:
        String username = "test123"
        String password = "test123"
        RegisterUserDTO registerUserDTO = new RegisterUserDTO(
                username,
                password,
                Set.of(Role.ADMIN)
        )
        when:
        UserDTO userDTO = authUtil.register(registerUserDTO);

        then:
        userDTO.getUsername() == "test123"
        userDTO.getAuthorities()[0] == Role.ADMIN
        userDTO.getId() != null
        noExceptionThrown()
    }

    def "User can't register with invalid info"() {
        given:
        String username = "1"
        String password = null
        RegisterUserDTO registerUserDTO = new RegisterUserDTO(
                username,
                password,
                Set.of(Role.ADMIN)
        )

        when:
        ErrorResponse errorResponse = authUtil.register(registerUserDTO, status().isBadRequest());

        then:
        errorResponse != null
        errorResponse.message == "Method argument validation failed"
        errorResponse.details.size() == 2
    }
}
