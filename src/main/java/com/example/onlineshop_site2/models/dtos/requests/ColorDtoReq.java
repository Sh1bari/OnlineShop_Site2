package com.example.onlineshop_site2.models.dtos.requests;

import com.example.onlineshop_site2.models.entities.Color;
import com.example.onlineshop_site2.models.entities.Good;
import com.example.onlineshop_site2.models.entities.Size;
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
public class ColorDtoReq {
    private String name;
    private String code;
    private List<SizeDtoReq> sizes;
    
    public Color mapToEntity(){
        return Color.builder()
                .name(name)
                .code(code)
                .colorType(ColorType.WITH_SIZE)
                .sizes(sizes.stream().map(SizeDtoReq::mapToEntity).toList())
                .build();
    }
}
