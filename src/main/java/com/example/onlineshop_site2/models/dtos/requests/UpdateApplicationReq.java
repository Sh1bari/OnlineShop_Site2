package com.example.onlineshop_site2.models.dtos.requests;

import com.example.onlineshop_site2.models.enums.ApplicationStatus;
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
public class UpdateApplicationReq {
    private String adminComment;
    private ApplicationStatus status;
}
