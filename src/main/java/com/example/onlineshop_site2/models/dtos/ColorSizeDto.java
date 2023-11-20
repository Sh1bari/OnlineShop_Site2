package com.example.onlineshop_site2.models.dtos;

import com.example.onlineshop_site2.models.entities.Color;
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
public class ColorSizeDto {
    private Long id;
    private String name;
    private String code;

    public static ColorSizeDto mapFromEntity(Color colorSizeEntity) {
        return ColorSizeDto.builder()
                .id(colorSizeEntity.getId())
                .name(colorSizeEntity.getName())
                .code(colorSizeEntity.getCode())
                .build();
    }

}
