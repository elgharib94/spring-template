package com.spring.template.integration.util

import com.spring.template.domain.dto.AuthRequest
import com.spring.template.domain.dto.RegisterUserDTO
import com.spring.template.domain.dto.UserDTO
import com.spring.template.domain.exceptions.ErrorResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.test.web.servlet.ResultMatcher

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Component
class AuthUtil {
    private static final AUTH_URL = "/api/public"

    @Autowired
    private HttpUtil httpUtil;

    UserDTO login(AuthRequest authRequest) {
        return httpUtil.post(AUTH_URL + "/login", authRequest, status().isOk(), UserDTO);
    }

    ErrorResponse login(AuthRequest authRequest, ResultMatcher status) {
        return httpUtil.post(AUTH_URL + "/login", authRequest, status, ErrorResponse);
    }

    UserDTO register(RegisterUserDTO dto) {
        return httpUtil.post(AUTH_URL + "/register", dto, status().isOk(), UserDTO);
    }

    ErrorResponse register(RegisterUserDTO dto, ResultMatcher status) {
        return httpUtil.post(AUTH_URL + "/register", dto, status, ErrorResponse);
    }
}
