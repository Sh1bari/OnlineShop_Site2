package com.example.onlineshop_site2.models.dtos.responses;

import com.example.onlineshop_site2.models.entities.FiveCategory;
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
public class FiveCategoryItemRes {
    private Long id;

    private String path;

    private Long categoryId;

    private String categoryName;

    public static FiveCategoryItemRes mapFromEntity(FiveCategory o){
        FiveCategoryItemRes builder = FiveCategoryItemRes.builder()
                .id(o.getId())
                .path(o.getPhoto() != null ? o.getPhoto().getPath() : null)
                .categoryId(o.getCategory().getId())
                .categoryName(o.getCategory() != null ? o.getCategory().getName() : null)
                .build();
        return builder;
    }
}
