package com.assignment.service;

import com.assignment.dto.UserRegisterDto;
import com.assignment.entity.UserEntity;
import com.assignment.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success(){
        UserRegisterDto dto = new UserRegisterDto("TEST NAME","testemail@example.com","password123");
        String encodedPassword = "encodedPassword";

        when(passwordEncoder.encode(dto.getPassword())).thenReturn(encodedPassword);
        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(userRepository.save(any(UserEntity.class))).thenReturn(new UserEntity());

        userService.registerUser(dto, encodedPassword);

        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testUserAlreadyExists(){
        when(userRepository.existsByEmail("existing@example.com")).thenReturn(true);

        boolean exists = userService.existsByEmail("existing@example.com");
        assertTrue(exists);
    }
}
