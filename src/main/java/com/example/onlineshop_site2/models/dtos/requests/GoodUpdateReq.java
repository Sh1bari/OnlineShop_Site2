package com.example.onlineshop_site2.models.dtos.requests;

import com.example.onlineshop_site2.models.dtos.requests.BackColorDtoReq;
import com.example.onlineshop_site2.models.entities.Good;
import com.example.onlineshop_site2.models.enums.ColorType;
import lombok.*;

import java.util.ArrayList;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodUpdateReq {
    private String name;

    private String description;

    private String compound;

    private BackColorDtoReq backColor;

    public Good mapToEntity() {
        Good build = Good.builder()
                .name(name)
                .description(description)
                .compound(compound)
                .backColor(backColor.mapToEntity())
                .build();
        build.getBackColor().setGoodBackColor(build);
        build.getBackColor().setGood(build);
        build.getBackColor().setColorType(ColorType.BACKGROUND);
        return build;
    }
}
