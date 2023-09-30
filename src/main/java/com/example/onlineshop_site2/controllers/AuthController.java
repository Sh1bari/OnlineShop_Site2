package com.example.onlineshop_site2.controllers;

import com.example.onlineshop_site2.models.dtos.JwtRequest;
import com.example.onlineshop_site2.models.dtos.RegistrationUserDto;
import com.example.onlineshop_site2.services.security.SecurityAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final SecurityAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        return authService.createAuthToken(authRequest);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        return authService.createNewUser(registrationUserDto);
    }

    @GetMapping("/reset-token")
    public ResponseEntity<?> resetToken(Principal principal) {
        return authService.resetToken(principal.getName());
    }
}