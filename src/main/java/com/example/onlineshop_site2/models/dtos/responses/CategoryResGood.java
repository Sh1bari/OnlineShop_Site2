package com.example.onlineshop_site2.models.dtos.responses;

import lombok.*;
import org.springframework.data.domain.Page;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResGood {
    private String name;

    private Page<GoodResDto> goods;
}
