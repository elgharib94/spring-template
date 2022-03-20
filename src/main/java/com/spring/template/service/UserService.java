package com.spring.template.service;

import com.spring.template.domain.dto.RegisterUserDTO;
import com.spring.template.domain.dto.UpdatePasswordDTO;
import com.spring.template.domain.dto.UserDTO;
import com.spring.template.domain.model.User;
import com.spring.template.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.ValidationException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDTO create(RegisterUserDTO registerUserDTO) {
        User user = User.builder()
                .username(registerUserDTO.getUsername())
                .password(passwordEncoder.encode(registerUserDTO.getPassword()))
                .authorities(registerUserDTO.getAuthorities())
                .build();

        return userRepository.save(user).asDTO();
    }

    @Transactional
    public UserDTO update(UUID userId, UserDTO userDTO) {
        User user = userRepository.getById(userId);
        user.update(userDTO);

        return userRepository.save(user).asDTO();
    }

    @Transactional
    public UserDTO updatePassword(UUID userId, UpdatePasswordDTO updatePasswordDTO) throws ValidationException {
        User user = userRepository.getById(userId);

        if (!passwordEncoder.matches(updatePasswordDTO.getOldPassword(), user.getPassword())) {
            throw new ValidationException("Old password does not match the current one, Please ");
        }

        if (passwordEncoder.encode(updatePasswordDTO.getNewPassword()).equals(user.getPassword())) {
            throw new ValidationException("Old Password and New Password cannot be same, Please update the new one");
        }

        user.updatePassword(passwordEncoder.encode(updatePasswordDTO.getNewPassword()));

        return userRepository.save(user).asDTO();
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(User::asDTO);
    }

    public UserDTO getUser(UUID id) {
        return userRepository.getById(id).asDTO();
    }

    @Transactional
    public void delete(UUID userId) {
        User user = userRepository.getById(userId);
        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public UserDTO getByName(String username) {
        return userRepository.getByName(username).asDTO();
    }
}
