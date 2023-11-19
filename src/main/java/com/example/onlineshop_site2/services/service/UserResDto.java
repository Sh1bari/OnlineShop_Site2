package com.example.onlineshop_site2.services.service;

import com.example.onlineshop_site2.models.entities.User;
import com.example.onlineshop_site2.models.enums.Gender;
import com.example.onlineshop_site2.models.enums.Newsletter;
import lombok.*;

import java.time.LocalDate;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResDto {
    private String email;

    private String firstName;

    private String lastName;

    private Gender gender;

    private LocalDate DOB;

    private String country;

    private String state;
    private String city;

    private String phone;

    private Newsletter newsletter;

    public static UserResDto mapFromEntity(User user) {
        return UserResDto.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .gender(user.getGender())
                .DOB(user.getDOB())
                .country(user.getCountry())
                .state(user.getState())
                .city(user.getCity())
                .phone(user.getPhone())
                .newsletter(user.getNewsletter())
                .build();
    }
}
