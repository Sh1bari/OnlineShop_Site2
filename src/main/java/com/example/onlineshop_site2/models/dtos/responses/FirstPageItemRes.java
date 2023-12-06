package com.example.onlineshop_site2.models.dtos.responses;

import com.example.onlineshop_site2.models.entities.FirstPage;
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
public class FirstPageItemRes{
 private Long id;

 private String name;

 private String description;

 private Long goodId;

 private String path;

 public static FirstPageItemRes mapFromEntity(FirstPage o){
  FirstPageItemRes builder = FirstPageItemRes.builder()
          .name(o.getName())
          .description(o.getDescription())
          .id(o.getId())
          .goodId(o.getGood().getId())
          .path(o.getPhoto().getPath())
          .build();
  return builder;
 }
}
