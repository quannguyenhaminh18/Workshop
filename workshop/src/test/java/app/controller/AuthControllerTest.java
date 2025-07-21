package app.controller;

import app.dto.AuthInfoRequest;
import app.exception.ConflictException;
import app.security.TestSecurityConfig;
import app.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest(AuthController.class)
@Import(TestSecurityConfig.class)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthService authService;

    private final String REGISTER_URL = "/auth/register";

    @Test
    void registerSuccess() throws Exception {
        AuthInfoRequest request = new AuthInfoRequest();
        request.setUsername("ValidUser");
        request.setPassword("Valid@123");

        doNothing().when(authService).register(any(AuthInfoRequest.class));

        mockMvc.perform(post(REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void registerReturn400() throws Exception {
        AuthInfoRequest request = new AuthInfoRequest();
        request.setUsername("");
        request.setPassword("Valid@123");

        mockMvc.perform(post(REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerReturn409() throws Exception {
        AuthInfoRequest request = new AuthInfoRequest();
        request.setUsername("ExistingUser");
        request.setPassword("Valid@123");

        doThrow(new ConflictException("username.exist"))
                .when(authService).register(any(AuthInfoRequest.class));

        mockMvc.perform(post(REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    void registerReturn500() throws Exception {
        AuthInfoRequest request = new AuthInfoRequest();
        request.setUsername("ValidUser");
        request.setPassword("Valid@123");

        doThrow(new RuntimeException("Unexpected error"))
                .when(authService).register(any(AuthInfoRequest.class));

        mockMvc.perform(post(REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void login() {

    }
}