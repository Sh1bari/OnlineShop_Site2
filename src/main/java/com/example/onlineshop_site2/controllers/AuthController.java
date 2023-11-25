package com.example.onlineshop_site2.controllers;

import com.example.onlineshop_site2.models.dtos.*;
import com.example.onlineshop_site2.models.dtos.requests.CodeReq;
import com.example.onlineshop_site2.services.security.SecurityAuthService;
import com.example.onlineshop_site2.services.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
public class AuthController {
    private final SecurityAuthService authService;

    @Operation(summary = "Авторизация")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = JwtResponse.class))})
    })
    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        return authService.createAuthToken(authRequest);
    }
    @Operation(summary = "Регистрация")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Код создан")
    })
    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody @Valid RegistrationUserDto registrationUserDto) {
        return authService.createNewUser(registrationUserDto);
    }
    @Operation(summary = "Обновить токен, только авторизованные")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = JwtResponse.class))})
    })
    @Secured("ROLE_USER")
    @GetMapping("/reset-token")
    public ResponseEntity<?> resetToken(Principal principal) {
        return authService.resetToken(principal.getName());
    }

    private final EmailService emailService;

    @Operation(summary = "Отправить код")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Отправлено"),
            @ApiResponse(responseCode = "404", description = "Не отправлено")
    })
    @PostMapping("/sendCode")
    public ResponseEntity<?> sendCode(@RequestBody @Valid CodeReq req) {
        if(emailService.sendCode(req.getEmail())){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .build();
        }else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @Operation(summary = "Проверить код и зарегаться")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))})
    })
    @PostMapping("/checkCode/{code}")
    public ResponseEntity<?> checkCode(
            @RequestBody @Valid RegistrationUserDto registrationUserDto,
            @PathVariable(name = "code")String code) {
        return authService.confirmCode(registrationUserDto, code);
    }

}