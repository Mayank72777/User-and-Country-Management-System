package com.assignment.service;

import com.assignment.dto.*;

import java.util.Optional;

public interface UserService {

    void registerUser(UserRegisterDto registerDto, String encodedPassword);

    Optional<String> authenticateUser(UserLoginDto loginDto);

    boolean existsByEmail(String email);

    UserDto getUserByEmail(String email);

    UserDto updateUser(String email, UserUpdateDto updateDto);

    void deleteUser(String email);

    void updatePassword(String email, PasswordUpdateDto passwordUpdateDto);
}


