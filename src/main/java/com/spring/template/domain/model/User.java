package com.spring.template.domain.model;

import com.spring.template.domain.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Set<Role> authorities;

    public void update(UserDTO userDTO) {
        if (!StringUtils.isEmpty(userDTO.getUsername()))
            this.username = userDTO.getUsername();

        if (!StringUtils.isEmpty(userDTO.getUsername()))
            this.authorities = userDTO.getAuthorities();
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public UserDTO asDTO() {
        return new UserDTO(
                id,
                username,
                authorities
        );
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
