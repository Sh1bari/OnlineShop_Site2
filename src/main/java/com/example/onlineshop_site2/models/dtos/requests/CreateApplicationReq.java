package com.example.onlineshop_site2.models.dtos.requests;

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
public class CreateApplicationReq {
    private String clientComment;
}
