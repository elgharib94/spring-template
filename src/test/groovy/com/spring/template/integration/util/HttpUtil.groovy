package com.spring.template.integration.util

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.ResultMatcher

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*

@Component
class HttpUtil {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    def <T> T post(String url, Object objectBody, ResultMatcher expectedStatus, Class<T> responseType) {
        String stringResponse =
                post(url, objectBody, expectedStatus)
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        return StringUtils.isEmpty(stringResponse) ? null : objectMapper.readValue(stringResponse, responseType);
    }

    ResultActions post(String url, Object objectBody, ResultMatcher expectedStatus) throws Exception {
        return mockMvc
                .perform(post(url)
                        .content(objectMapper.writeValueAsString(objectBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(expectedStatus);
    }

    ResultActions put(String url, Object objectBody, ResultMatcher expectedStatus) throws Exception {
        return mockMvc
                .perform(put(url)
                        .content(objectMapper.writeValueAsString(objectBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(expectedStatus);
    }

    ResultActions patch(String url, Object objectBody, ResultMatcher expectedStatus) throws Exception {
        return mockMvc
                .perform(patch(url)
                        .content(objectMapper.writeValueAsString(objectBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(expectedStatus);
    }

    ResultActions delete(String url, Object objectBody, ResultMatcher expectedStatus) throws Exception {
        return mockMvc
                .perform(delete(url)
                        .content(objectMapper.writeValueAsString(objectBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(expectedStatus);
    }

    def <T> T get(String url, ResultMatcher expectedStatus, Class<T> responseType) throws Exception {
        String response = mockMvc
                .perform(get(url))
                .andExpect(expectedStatus)
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(response, responseType);
    }
}
