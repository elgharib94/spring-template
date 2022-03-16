package com.spring.template.repository;

import com.spring.template.domain.exceptions.NotFoundException;
import com.spring.template.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Page<User> findAll(Pageable pageable);

    Optional<User> findByUsername(String username);

    default User getById(UUID id) {
        return this.findById(id).orElseThrow(() -> new NotFoundException(User.class, String.valueOf(id)));
    }

    default User getByName(String username) {
        return this.findByUsername(username).orElseThrow(() -> new NotFoundException(User.class, username));
    }
}