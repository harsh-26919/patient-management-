package com.harsh.authservice.dto;

import lombok.Data;

@Data
public class LoginResponseDTO
{
    private final String token;

    public LoginResponseDTO(String token) {
        this.token = token;
    }

}
