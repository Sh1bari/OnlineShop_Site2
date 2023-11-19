package com.example.onlineshop_site2.models.dtos.responses;

import com.example.onlineshop_site2.models.entities.Good;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodIdsRes {
    private Long id;
    public static GoodIdsRes mapFromEntity(Good good){
        return GoodIdsRes.builder()
                .id(good.getId())
                .build();
    }
    public static List<GoodIdsRes> mapFromEntitySet(Set<Good> good){
        List<GoodIdsRes> res = new ArrayList<>();
        good.forEach(o->{
            res.add(GoodIdsRes.builder()
                    .id(o.getId())
                    .build());
        });
        return res;
    }
}
