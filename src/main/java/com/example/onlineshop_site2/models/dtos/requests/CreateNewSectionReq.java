package com.example.onlineshop_site2.models.dtos.requests;

import jakarta.validation.constraints.NotBlank;
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
public class CreateNewSectionReq {
    @NotBlank
    private String name;
}
