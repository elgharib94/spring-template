package com.spring.template.repository

import com.spring.template.domain.dto.UserDTO
import com.spring.template.domain.exceptions.NotFoundException
import com.spring.template.domain.model.Role
import com.spring.template.domain.model.User
import com.spring.template.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

@DataJpaTest
class UserRepositoryTest extends Specification {

    @Autowired
    private UserRepository userRepository;

    def "User can be found by Id"() {
        given:
        User user = User.builder()
                .username("elgharib94")
                .password("Test1234")
                .authorities(Set.of(Role.ADMIN))
                .build();

        user = userRepository.save(user);

        when:
        UserDTO expected = userRepository.getById(user.getId()).asDTO();

        then:
        expected.getUsername() == "elgharib94"
        expected.getAuthorities().size() == 1
    }

    def "Should return NotFoundException if user not found"() {
        given:
        UUID generatedId = UUID.randomUUID();

        when:
        userRepository.getById(generatedId).asDTO();

        then:
        thrown(NotFoundException)
    }
}