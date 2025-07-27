package com.harsh.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequestDTO
{
    @NotBlank(message = "Email is Required")
    @Email(message = "Email should be valid email address")
    private String email;
    @NotBlank(message = "Password is Required")
    @Size(min = 8, message = "Password must be minimum 8 characters")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
