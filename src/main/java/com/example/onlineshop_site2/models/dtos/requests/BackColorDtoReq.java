package com.example.onlineshop_site2.models.dtos.requests;

import com.example.onlineshop_site2.models.entities.Color;
import com.example.onlineshop_site2.models.enums.ColorType;
import lombok.*;

import java.util.List;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BackColorDtoReq {
    private String name;
    private String code;

    public Color mapToEntity(){
        return Color.builder()
                .name(name)
                .colorType(ColorType.BACKGROUND)
                .code(code)
                .build();
    }
}
