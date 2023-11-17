package com.example.onlineshop_site2.models.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CategoryCreateReq {
    @NotBlank
    private String name;
    @NotNull
    private Long sectionId;
}
