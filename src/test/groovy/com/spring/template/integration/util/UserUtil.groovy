package com.spring.template.integration.util

import com.spring.template.domain.dto.UserDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Component
class UserUtil {

    private static final USER_URL = "/api/users"

    @Autowired
    private HttpUtil httpUtil;

    ResponseEntity<Page<UserDTO>> getUsers() {
        return httpUtil.get(USER_URL, status().isOk(), ResponseEntity<Page<UserDTO>>);
    }

}
