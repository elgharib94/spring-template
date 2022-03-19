package com.spring.template.service

import com.spring.template.domain.dto.RegisterUserDTO
import com.spring.template.domain.dto.UserDTO
import com.spring.template.domain.model.Role
import com.spring.template.domain.model.User
import com.spring.template.repository.UserRepository
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.*
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

@DataJpaTest
class UserServiceTest extends Specification {

    private UserRepository userRepository = Mock();

    private PasswordEncoder passwordEncoder = Mock();

    private UserService userService;

    def setup() {
        userService = new UserService(userRepository, passwordEncoder);
    }

    def "User can be created"() {
        given:
        User user = new User("username", "password", Set.of(Role.USER))
        and:
        userRepository.save(_ as User) >> user
        passwordEncoder.encode(user.getPassword()) >> "password"

        when:
        RegisterUserDTO registerUserDTO = new RegisterUserDTO("username", "password", Set.of(Role.USER))
        UserDTO expected = userService.create(registerUserDTO)

        then:
        expected.id == user.id
        expected.username == user.username
        expected.authorities == user.authorities
    }

    def "User can be updated"() {
        given:
        User user = new User("username", "password", Set.of(Role.USER))
        and:
        userRepository.getById(_ as UUID) >> user
        userRepository.save(_ as User) >> user

        when:
        UserDTO updatedDto = userService.update(user.getId(), user.asDTO())

        then:
        updatedDto.id == user.id
        updatedDto.username == user.username
        updatedDto.authorities == user.authorities
    }

    def "Users can be gotten"() {
        given:
        User user1 = new User("username1", "password", Set.of(Role.USER))
        User user2 = new User("username2", "password", Set.of(Role.ADMIN))
        User user3 = new User("username3", "password", Set.of(Role.MANAGER))

        and:
        userRepository.findAll(_ as Pageable) >> new PageImpl<>(List.of(user1, user2, user3));

        when:
        Page<UserDTO> userDTOPage = userService.getUsers(createPageRequest())

        then:
        userDTOPage.totalPages == 1
        userDTOPage.size == 3
        userDTOPage.content.get(0).getId() == user1.asDTO().getId()
        userDTOPage.content.get(1).getId() == user2.asDTO().getId()
        userDTOPage.content.get(2).getId() == user3.asDTO().getId()
    }

    def "User can be gotten By Id"() {
        given:
        User user1 = new User("username1", "password", Set.of(Role.USER))

        and:
        userRepository.getById(_ as UUID) >> user1;

        when:
        UserDTO userDTO = userService.getUser(user1.getId())

        then:
        userDTO.getId() == user1.asDTO().getId()
        userDTO.getUsername() == user1.asDTO().getUsername()
        userDTO.getAuthorities() == user1.asDTO().getAuthorities()
    }

    def "User can be gotten By Name"() {
        given:
        User user1 = new User("username1", "password", Set.of(Role.USER))

        and:
        userRepository.getByName(_ as String) >> user1;

        when:
        UserDTO userDTO = userService.getByName(user1.getUsername())

        then:
        userDTO.getId() == user1.asDTO().getId()
        userDTO.getUsername() == user1.asDTO().getUsername()
        userDTO.getAuthorities() == user1.asDTO().getAuthorities()
    }

    def "User can be deleted"() {
        given:
        User user = new User("username", "password", Set.of(Role.USER))
        and:
        userRepository.getById(_ as UUID) >> user

        when:
        userService.delete(user.getId())

        then:
        1 * userRepository.delete(user)
    }

    private Pageable createPageRequest() {
        Pageable pageable = PageRequest.of(
                0,
                5,
                Sort.by(Sort.Order.asc("username"))
        );

        return pageable;
    }
}
