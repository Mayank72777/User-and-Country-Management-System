package com.assignment.controller;

import com.assignment.dto.PasswordUpdateDto;
import com.assignment.dto.UserDto;
import com.assignment.dto.UserUpdateDto;
import com.assignment.security.JwtUtil;
import com.assignment.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User Management", description = "APIs for managing users")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Operation(summary = "Get logged-in user details")
    @GetMapping("/me")
    public ResponseEntity<UserDto> getUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserDto userDto = userService.getUserByEmail(email);
        if(userDto != null){
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Update logged-in user details (ex- name, email)")
    @PutMapping("/me")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserUpdateDto userUpdateDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        UserDto updatedUser = userService.updateUser(email, userUpdateDto);
        if(updatedUser != null){
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @Operation(summary = "Delete logged-in user")
    @DeleteMapping("/me")
        public ResponseEntity<String> deleteUser(){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            userService.deleteUser(email);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);

    }

    @Operation(summary = "Update logged-in user password")
    @PutMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestHeader("Authorization") String token,
                                                 @RequestBody PasswordUpdateDto passwordUpdateDto){
        String email = jwtUtil.extractEmail(token.substring(7));
        userService.updatePassword(email, passwordUpdateDto);
        return new ResponseEntity<>("Password updated successfully.",HttpStatus.OK);
    }
}
