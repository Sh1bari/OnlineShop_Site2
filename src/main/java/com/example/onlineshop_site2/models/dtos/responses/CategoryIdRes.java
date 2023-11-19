package com.example.onlineshop_site2.models.dtos.responses;

import com.example.onlineshop_site2.models.entities.Category;
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
public class CategoryIdRes {
    private Long id;
    public static CategoryIdRes mapFromEntity(Category category){
        return CategoryIdRes.builder()
                .id(category.getId())
                .build();
    }
}
