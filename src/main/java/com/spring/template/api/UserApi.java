package com.spring.template.api;

import com.spring.template.authorization.RequiresAdminOrManagerRole;
import com.spring.template.authorization.RequiresAnyRole;
import com.spring.template.domain.dto.UpdatePasswordDTO;
import com.spring.template.domain.dto.UserDTO;
import com.spring.template.domain.model.Role;
import com.spring.template.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
@Tag(name = "User")
public class UserApi {

    private final UserService userService;

    @RequiresAdminOrManagerRole
    @GetMapping
    public ResponseEntity<Page<UserDTO>> getUsers(@PageableDefault(sort = "username", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok().body(userService.getUsers(pageable));
    }

    @PreAuthorize("#id == principal.id or hasRole('" + Role.Code.ADMIN + "')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@Param("id") @PathVariable("id") UUID id) {
        return ResponseEntity.ok().body(userService.getUser(id));
    }

    @RequiresAnyRole
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getMe(Principal principal) {
        return ResponseEntity.ok().body(userService.getByName(principal.getName()));
    }

    @RequiresAdminOrManagerRole
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") UUID id) {
        userService.delete(id);
    }

    @RequiresAdminOrManagerRole
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") UUID id, @Valid @RequestBody UserDTO userDTO) {
        userService.update(id, userDTO);
        return ResponseEntity.ok().body(userService.getUser(id));
    }

    @PatchMapping("/password/{id}")
    public ResponseEntity<UserDTO> updatePassword(@PathVariable("id") UUID id, @Valid @RequestBody UpdatePasswordDTO updatePasswordDTO) throws ValidationException {
        return ResponseEntity.ok().body(userService.updatePassword(id, updatePasswordDTO));
    }
}
