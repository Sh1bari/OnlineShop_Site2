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
    private String path;
    private Integer position;
    public static PhotoIdRes mapFromEntity(Photo photoEntity) {
        return PhotoIdRes.builder()
                .id(photoEntity.getId())
                .path(photoEntity.getPath())
                .position(photoEntity.getPosition())
                .build();
    }
}
