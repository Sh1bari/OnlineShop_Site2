package com.example.onlineshop_site2.models.dtos.requests;

import com.example.onlineshop_site2.models.enums.Gender;
import com.example.onlineshop_site2.models.enums.Newsletter;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Data
@Validated
public class ChangePasswordReq {
    @Email(message = "Not valid email")
    private String email;
    private String password;
    private String confirmPassword;
}
