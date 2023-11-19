package com.example.onlineshop_site2.models.dtos;

import com.example.onlineshop_site2.models.enums.Gender;
import com.example.onlineshop_site2.models.enums.Newsletter;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Data
@Validated
public class RegistrationUserDto {
    @Email(message = "Not valid email")
    private String email;
    private String password;
    private String confirmPassword;

    private String firstName;

    private String lastName;

    private Gender gender;

    private LocalDate DOB;

    private String country;

    private String state;
    private String city;

    private String phone;

    private Newsletter newsletter;
}
