package com.assignment.controller;

import com.assignment.dto.UserLoginDto;
import com.assignment.dto.UserRegisterDto;
import com.assignment.security.JwtUtil;
import com.assignment.security.TestSecurityConfig;
import com.assignment.service.UserDetailsServiceImpl;
import com.assignment.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestSecurityConfig.class)
@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

//    @InjectMocks
//    private AuthController authController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        objectMapper = new ObjectMapper();
    }

    @Test
    void testRegisterUser_Success() throws Exception{
        UserRegisterDto userDto = new UserRegisterDto("Test Name","test1@example.com","password123");
        Mockito.when(userService.existsByEmail("test1@example.com")).thenReturn(false);
        Mockito.doNothing().when(userService).registerUser(Mockito.any(),Mockito.any());

        mockMvc.perform(post("/api/auth/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
              .andExpect(status().isCreated())
              .andExpect(content().string("User registered successfully"));
    }

    @Test
    void testLogin_Failure_InvalidCredentials() throws Exception {
        UserLoginDto loginDto = new UserLoginDto("wrong@example.com", "wrongpassword");

        Mockito.when(userService.authenticateUser(Mockito.any())).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid email or password"));
    }
}
