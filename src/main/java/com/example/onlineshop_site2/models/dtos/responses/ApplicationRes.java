package com.example.onlineshop_site2.models.dtos.responses;

import com.example.onlineshop_site2.models.dtos.GoodItemDto;
import com.example.onlineshop_site2.models.entities.Application;
import com.example.onlineshop_site2.models.entities.User;
import com.example.onlineshop_site2.models.enums.ApplicationStatus;
import lombok.*;

import java.time.LocalDateTime;
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
public class ApplicationRes {
    private Long id;
    private Long userId;
    private List<GoodItemDto> items;
    private String clientComment;
    private String adminComment;
    private LocalDateTime time;
    private ApplicationStatus status;

    public static ApplicationRes mapFromEntity(Application application) {
        List<GoodItemDto> items = new ArrayList<>();
        application.getGoodItems().forEach(o->{
            items.add(GoodItemDto.mapFromEntity(o));
        });

        ApplicationRes builder = ApplicationRes.builder()
                .id(application.getId())
                .userId(application.getUser().getId())
                .items(items)
                .clientComment(application.getClientComment())
                .adminComment(application.getAdminComment())
                .time(application.getTime())
                .status(application.getStatus())
                .build();
        return builder;
    }
}
