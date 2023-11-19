package com.example.onlineshop_site2.models.dtos.requests;

import jakarta.validation.constraints.Min;
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
public class UserBagReq {
    @Min(value = 1, message = "id cant be less than 1")
    private Long goodId;

    @Min(value = 1, message = "Amount cant be less than 1")
    private Integer amount;

}
