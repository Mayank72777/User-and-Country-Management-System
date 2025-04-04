package com.assignment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SecureController {

    @Operation(summary = "Get secured data", description = "Returns secured data only accessible with a valid JWT token")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/secure-data")

    public String getSecuredData(){
        return "This is secured";
    }
}
