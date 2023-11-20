package com.example.onlineshop_site2.models.dtos.responses;

import com.example.onlineshop_site2.models.entities.Good;
import com.example.onlineshop_site2.models.entities.UserBag;
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
public class UserBagRes {

    private Long id;

    private Long goodId;

    private Long sizeId;

    private Integer amount;
    public static UserBagRes mapFromEntity(UserBag userBag){
        return UserBagRes.builder()
                .id(userBag.getId())
                .goodId(userBag.getGood().getId())
                .sizeId(userBag.getSize().getId())
                .amount(userBag.getAmount())
                .build();
    }

    public static List<UserBagRes> mapFromEntitySet(Set<UserBag> userBag){
        List<UserBagRes> res = new ArrayList<>();
        userBag.forEach(o->{
            res.add(UserBagRes.builder()
                    .id(o.getId())
                    .goodId(o.getGood().getId())
                    .sizeId(o.getSize().getId())
                    .amount(o.getAmount())
                    .build());
        });
        return res;
    }
}
