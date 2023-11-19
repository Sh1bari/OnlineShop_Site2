package com.example.onlineshop_site2.models.dtos.requests;

import com.example.onlineshop_site2.models.dtos.requests.SizeDtoReq;
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
public class GoodAddSizeReq {
    private List<SizeDtoReq> sizes;
    public Good mapToEntity(){
        Good build = Good.builder()
                .sizes(sizes.stream().map(o -> o.mapToEntity()).toList())
                .build();
        build.getSizes().forEach(o->{
            o.setSizeType(SizeType.SIZE);
        });
        return build;
    }
}
