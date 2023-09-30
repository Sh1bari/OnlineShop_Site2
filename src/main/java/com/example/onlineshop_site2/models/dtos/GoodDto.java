package com.example.onlineshop_site2.models.dtos;

import com.example.onlineshop_site2.models.entities.Good;
import com.example.onlineshop_site2.models.entities.Photo;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Data
@Builder
public class GoodDto {
    private Long id;
    private String name;
    private Long photoId;

    public static GoodDto mapToGoodDto(Good good){
        Long photoIdVar = null;
        Optional<Long> photoId = good.getPhotos().stream()
                .filter(photo -> photo.getPosition() == 0)
                .map(Photo::getId)
                .findFirst();
        if (photoId.isPresent()){
            photoIdVar = photoId.get();
        }
        return GoodDto.builder()
                .id(good.getId())
                .name(good.getName())
                .photoId(photoIdVar)
                .build();
    }
}
