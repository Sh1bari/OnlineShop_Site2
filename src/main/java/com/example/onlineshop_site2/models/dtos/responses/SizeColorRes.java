package com.example.onlineshop_site2.models.dtos.responses;

import com.example.onlineshop_site2.models.dtos.ColorSizeDto;
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
public class SizeColorRes {

    private Long id;
    private String size;

    @Enumerated(EnumType.STRING)
    private SizeStatus sizeStatus;

    private ColorSizeDto color;

    public static SizeColorRes mapFromEntity(Size sizeEntity) {
        return SizeColorRes.builder()
                .id(sizeEntity.getId())
                .size(sizeEntity.getSize())
                .sizeStatus(sizeEntity.getSizeStatus())
                .color(ColorSizeDto.mapFromEntity(sizeEntity.getColor()))
                .build();
    }
}
