package com.harsh.authservice.controller;


import com.harsh.authservice.dto.LoginRequestDTO;
import com.harsh.authservice.dto.LoginResponseDTO;
import com.harsh.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AuthController
{
    private final AuthService authservice;
    public AuthController(AuthService authservice)
    {
        this.authservice = authservice;
    }
    @Operation(summary = "Genaerate token on user login")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO loginRequestDTO)
    {
        Optional<String> tokenOptional = authservice.authenticate(loginRequestDTO);

        if(tokenOptional.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = tokenOptional.get();
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @Operation(summary ="Validate token")
    @GetMapping("/validate")
    public ResponseEntity<Void> validate(@RequestHeader("Authorization") String authHeader)
    {
        //authorization: Bearer <token>
        if (authHeader == null || !authHeader.startsWith("Bearer "))
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return authservice.validateToken(authHeader.substring(7))
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
