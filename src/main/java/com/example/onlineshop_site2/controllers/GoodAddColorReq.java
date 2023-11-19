package com.example.onlineshop_site2.controllers;

import com.example.onlineshop_site2.models.dtos.requests.ColorDtoReq;
import com.example.onlineshop_site2.models.entities.Color;
import com.example.onlineshop_site2.models.entities.Good;
import com.example.onlineshop_site2.models.enums.SizeType;
import lombok.*;

import java.util.ArrayList;
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
public class GoodAddColorReq {
    private List<ColorDtoReq> colors;

    public Good mapToEntity(){
        List<Color> colors1 = new ArrayList<>();
        colors.forEach(o->colors1.add(o.mapToEntity()));
        Good build = Good.builder()
                .colors(colors1)
                .build();
        return build;
    }
}
