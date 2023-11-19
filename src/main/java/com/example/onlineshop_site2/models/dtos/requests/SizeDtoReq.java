package com.example.onlineshop_site2.models.dtos.requests;

import com.example.onlineshop_site2.models.entities.Size;
import com.example.onlineshop_site2.models.enums.SizeStatus;
import com.example.onlineshop_site2.models.enums.SizeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class SizeDtoReq {
    private Double size;

    @Enumerated(EnumType.STRING)
    private SizeStatus sizeStatus;

    public Size mapToEntity(){
        return Size.builder()
                .size(size)
                .sizeType(SizeType.COLOR_SIZE)
                .sizeStatus(sizeStatus)
                .build();
    }
}
