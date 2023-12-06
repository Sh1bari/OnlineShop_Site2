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
public class FirstPageReq {
    private String name;
    private String description;
    private Long goodId;
}
