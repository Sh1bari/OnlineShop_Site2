package com.example.onlineshop_site2.models.dtos.requests;

import com.example.onlineshop_site2.controllers.GoodController;
import com.example.onlineshop_site2.models.entities.Color;
import com.example.onlineshop_site2.models.entities.Good;
import com.example.onlineshop_site2.models.entities.Size;
import com.example.onlineshop_site2.models.enums.RecordState;
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
public class GoodCreateReq {
    private String name;

    private String description;

    private String compound;

    private BackColorDtoReq backColor;

    private List<ColorDtoReq> colors;
    
    private List<SizeDtoReq> sizes;
    
    public Good mapToEntity(){
        Good build = Good.builder()
                .name(name)
                .description(description)
                .compound(compound)
                .categories(new ArrayList<>())
                .photos(new ArrayList<>())
                .backColor(backColor.mapToEntity())
                .colors(colors.stream().map(o->o.mapToEntity()).toList())
                .sizes(sizes.stream().map(o -> o.mapToEntity()).toList())
                .state(RecordState.DRAFT)
                .build();
        build.getColors().forEach(o->o.setGood(build));
        build.getSizes().forEach(o->{
            o.setGood(build);
            o.setSizeType(SizeType.SIZE);
        });
        build.getBackColor().setGoodBackColor(build);
        build.getBackColor().setGood(build);
        return build;
    }
}
