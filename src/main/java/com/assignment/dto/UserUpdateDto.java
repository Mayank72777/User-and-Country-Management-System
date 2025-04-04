package com.assignment.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class UserUpdateDto {

    @NotBlank(message = "Name is required!")
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    public UserUpdateDto(){}

    public UserUpdateDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserUpdateDto{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
