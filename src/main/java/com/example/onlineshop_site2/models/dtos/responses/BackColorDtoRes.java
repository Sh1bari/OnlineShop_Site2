package com.example.onlineshop_site2.models.dtos.responses;

import com.example.onlineshop_site2.models.entities.Color;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BackColorDtoRes {
    private Long id;
    private String name;
    private String code;
    public static BackColorDtoRes mapFromEntity(Color colorEntity) {
        return BackColorDtoRes.builder()
                .id(colorEntity.getId())
                .name(colorEntity.getName())
                .code(colorEntity.getCode())
                .build();
    }
}
