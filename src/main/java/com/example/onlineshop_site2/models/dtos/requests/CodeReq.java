package com.example.onlineshop_site2.models.dtos.requests;

import jakarta.validation.constraints.Email;
import lombok.*;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeReq {
    @Email(message = "Not valid email")
    private String email;
}
