package com.assignment.controller;

import com.assignment.dto.UserLoginDto;
import com.assignment.dto.UserRegisterDto;
import com.assignment.security.JwtUtil;
import com.assignment.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@Tag(name = "User Auth Management", description = "APIs for managing user authentication")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Operation(
            summary = "Register a new user",
            description = "Registers a user with email and password",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User registered successfully"),
                    @ApiResponse(responseCode = "400", description = "User already exists")
            }
    )
    @PostMapping("/register")
    public ResponseEntity<String> register
            (@RequestBody UserRegisterDto userRegisterDto){
        if(userService.existsByEmail(userRegisterDto.getEmail())){
            return new ResponseEntity<>("User already exists!", HttpStatus.BAD_REQUEST);
        }
        String encodedPassword = passwordEncoder.encode(userRegisterDto.getPassword());

        userService.registerUser(userRegisterDto, encodedPassword);

        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);

    }
    @Operation(
            summary = "User login",
            description = "Authenticates user and returns a JWT token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login successful",
                            content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody UserLoginDto userLoginDto) {

        Optional<String> token = userService.authenticateUser(userLoginDto);

        if(token.isPresent()){
                return new ResponseEntity<>("Bearer "+token.get(),HttpStatus.OK);
            }
        return new ResponseEntity<>("Invalid email or password",HttpStatus.UNAUTHORIZED);
    }


}
