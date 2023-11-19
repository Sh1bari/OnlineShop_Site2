package com.example.onlineshop_site2.models.dtos.responses;

import com.example.onlineshop_site2.models.dtos.requests.SizeDtoReq;
import com.example.onlineshop_site2.models.entities.Color;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColorDtoRes {
    private Long id;
    private String name;
    private String code;
    private List<SizeDtoRes> sizes;

    public static ColorDtoRes mapFromEntity(Color colorEntity) {
        List<SizeDtoRes> sizeDtoResList = null;
        try {
            sizeDtoResList = colorEntity.getSizes().stream()
                    .map(SizeDtoRes::mapFromEntity)
                    .collect(Collectors.toList());
        }catch (Exception e){}

        return ColorDtoRes.builder()
                .id(colorEntity.getId())
                .name(colorEntity.getName())
                .code(colorEntity.getCode())
                .sizes(sizeDtoResList)
                .build();
    }
}
