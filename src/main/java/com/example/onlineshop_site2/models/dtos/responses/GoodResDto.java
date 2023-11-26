package com.example.onlineshop_site2.models.dtos.responses;

import com.example.onlineshop_site2.models.dtos.requests.ColorDtoReq;
import com.example.onlineshop_site2.models.dtos.requests.SizeDtoReq;
import com.example.onlineshop_site2.models.entities.Color;
import com.example.onlineshop_site2.models.entities.Good;
import com.example.onlineshop_site2.models.entities.Photo;
import com.example.onlineshop_site2.models.enums.ColorType;
import com.example.onlineshop_site2.models.enums.RecordState;
import com.example.onlineshop_site2.models.enums.SizeType;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Builder
public class GoodResDto {
    private Long id;
    private String name;
    private String description;

    private String compound;

    private RecordState state;
    private Double cost;

    private BackColorDtoRes backColor;

    private List<CategoryIdRes> categoryIds;

    private List<ColorDtoRes> colors;

    private List<SizeDtoRes> sizes;

    private List<PhotoIdRes> photos;

    public static GoodResDto mapFromEntity(Good goodEntity) {
        List<ColorDtoRes> colorDtoResList = goodEntity.getColors().stream()
                .filter(o->o.getColorType().equals(ColorType.WITH_SIZE))
                .map(ColorDtoRes::mapFromEntity)
                .collect(Collectors.toList());

        List<SizeDtoRes> sizeDtoResList = goodEntity.getSizes().stream()
                .filter(o->o.getSizeType().equals(SizeType.SIZE))
                .map(SizeDtoRes::mapFromEntity)
                .collect(Collectors.toList());

        List<PhotoIdRes> photoIdResList = null;
        try {
            photoIdResList = goodEntity.getPhotos().stream()
                    .map(PhotoIdRes::mapFromEntity)
                    .collect(Collectors.toList());
        }catch (Exception e){}

        List<CategoryIdRes> categoryIdRes = null;
        try{
            categoryIdRes = goodEntity.getCategories().stream()
                    .map(CategoryIdRes::mapFromEntity)
                    .collect(Collectors.toList());
        }catch (Exception e){}

        BackColorDtoRes backColor = null;
        try{
            backColor = BackColorDtoRes.mapFromEntity(goodEntity.getBackColor());
        }catch (Exception e){}

        return GoodResDto.builder()
                .id(goodEntity.getId())
                .cost(goodEntity.getCost())
                .name(goodEntity.getName())
                .state(goodEntity.getState())
                .description(goodEntity.getDescription())
                .compound(goodEntity.getCompound())
                .backColor(backColor)
                .categoryIds(categoryIdRes)
                .colors(colorDtoResList)
                .sizes(sizeDtoResList)
                .photos(photoIdResList)
                .build();
    }
}
