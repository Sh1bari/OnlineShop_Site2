package com.example.onlineshop_site2.services.security;

import com.example.onlineshop_site2.exceptions.AppError;
import com.example.onlineshop_site2.models.dtos.JwtRequest;
import com.example.onlineshop_site2.models.dtos.JwtResponse;
import com.example.onlineshop_site2.models.dtos.RegistrationUserDto;
import com.example.onlineshop_site2.models.dtos.UserDto;
import com.example.onlineshop_site2.models.dtos.requests.ChangePasswordReq;
import com.example.onlineshop_site2.models.entities.User;
import com.example.onlineshop_site2.services.service.EmailService;
import com.example.onlineshop_site2.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class SecurityAuthService {
    private final SecurityUserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;


    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getEmail());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
    private final EmailService emailService;

    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пароли не совпадают"), HttpStatus.BAD_REQUEST);
        }
        if (userService.findByEmail(registrationUserDto.getEmail()).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пользователь с указанным именем уже существует"), HttpStatus.BAD_REQUEST);
        }
        emailService.createCodeDB(registrationUserDto.getEmail());
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> confirmCode(@RequestBody RegistrationUserDto registrationUserDto,
                                         String code){
        emailService.checkCode(registrationUserDto.getEmail(), code);
        User user = userService.createNewUser(registrationUserDto);
        return ResponseEntity.ok(new UserDto(user.getId(), user.getEmail()));
    }

    public ResponseEntity<?> confirmPasCode(@RequestBody ChangePasswordReq req,
                                         String code){
        emailService.checkCode(req.getEmail(), code);
        return userService.changePas(req);
    }

    public ResponseEntity<?> resetToken(String username){
        UserDetails userDetails = userService.loadUserByUsername(username);
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
