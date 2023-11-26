package com.example.onlineshop_site2.models.dtos.responses;

import com.example.onlineshop_site2.models.entities.Photo;
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
public class PhotoIdRes {
    private Long id;
    private Integer position;
    public static PhotoIdRes mapFromEntity(Photo photoEntity) {
        return PhotoIdRes.builder()
                .id(photoEntity.getId())
                .position(photoEntity.getPosition())
                .build();
    }
}
