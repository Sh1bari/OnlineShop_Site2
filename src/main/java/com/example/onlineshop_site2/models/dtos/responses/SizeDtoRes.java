package com.example.onlineshop_site2.models.dtos.responses;

import com.example.onlineshop_site2.models.entities.Size;
import com.example.onlineshop_site2.models.enums.SizeStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class SizeDtoRes {

    private Long id;
    private String size;

    @Enumerated(EnumType.STRING)
    private SizeStatus sizeStatus;
    public static SizeDtoRes mapFromEntity(Size sizeEntity) {
        return SizeDtoRes.builder()
                .id(sizeEntity.getId())
                .size(sizeEntity.getSize())
                .sizeStatus(sizeEntity.getSizeStatus())
                .build();
    }
}
