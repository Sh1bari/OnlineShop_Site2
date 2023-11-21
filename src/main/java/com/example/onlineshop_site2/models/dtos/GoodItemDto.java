package com.example.onlineshop_site2.models.dtos;

import com.example.onlineshop_site2.models.entities.Good;
import com.example.onlineshop_site2.models.entities.GoodItem;
import com.example.onlineshop_site2.models.enums.RecordState;
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
public class GoodItemDto {
    private Long id;

    private Long goodId;

    private String colorName;

    private String colorCode;

    private String size;

    private Integer amount;

    private RecordState goodState;
    public static GoodItemDto mapFromEntity(GoodItem goodItem) {

        GoodItemDto builder = GoodItemDto.builder()
                .id(goodItem.getId())
                .goodId(goodItem.getGood().getId())
                .colorName(goodItem.getColorName())
                .colorCode(goodItem.getColorCode())
                .size(goodItem.getSize())
                .amount(goodItem.getAmount())
                .goodState(goodItem.getGood().getState())
                .build();
        return builder;
    }
}
