package ru.levelp.srv.person.profile.integration.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.levelp.srv.person.profile.infrastructure.annotation.ApiTest;

@ApiTest
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext
public abstract class AbstractController {

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mockMvc;

    @SneakyThrows
    protected MockHttpServletRequestBuilder preparePostRequest(String path, MediaType mediaType, Object body) {
        return MockMvcRequestBuilders.post(path)
                .content(objectMapper.writeValueAsString(body))
                .contentType(mediaType);
    }

    protected MockHttpServletRequestBuilder preparePostJsonContentRequest(String path, Object body) {
        return preparePostRequest(path, MediaType.APPLICATION_JSON, body);
    }

    @SneakyThrows
    protected MockHttpServletRequestBuilder prepareGetRequest(String path, MediaType mediaType) {
        return MockMvcRequestBuilders.get(path)
                .contentType(mediaType);
    }

    protected MockHttpServletRequestBuilder prepareGetJsonContentRequest(String path) {
        return prepareGetRequest(path, MediaType.APPLICATION_JSON);
    }

    @SneakyThrows
    protected MockHttpServletRequestBuilder prepareDeleteRequest(String uri, MediaType mediaType) {
        return MockMvcRequestBuilders.delete(uri)
                .contentType(mediaType);
    }

    protected MockHttpServletRequestBuilder prepareDeleteJsonContentRequest(String path) {
        return prepareDeleteRequest(path, MediaType.APPLICATION_JSON);
    }

    @SneakyThrows
    protected MockHttpServletRequestBuilder preparePutRequest(String path, MediaType mediaType, Object body) {
        return MockMvcRequestBuilders.put(path)
                .content(objectMapper.writeValueAsString(body))
                .contentType(mediaType);
    }

    protected MockHttpServletRequestBuilder preparePutJsonContentRequest(String path, Object body) {
        return preparePutRequest(path, MediaType.APPLICATION_JSON, body);
    }
}
