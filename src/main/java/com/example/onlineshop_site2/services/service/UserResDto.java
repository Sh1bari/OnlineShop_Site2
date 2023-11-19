package com.example.onlineshop_site2.services.service;

import com.example.onlineshop_site2.models.dtos.responses.GoodIdsRes;

import com.example.onlineshop_site2.models.dtos.responses.UserBagRes;
import com.example.onlineshop_site2.models.entities.User;
import com.example.onlineshop_site2.models.entities.UserBag;
import com.example.onlineshop_site2.models.enums.Gender;
import com.example.onlineshop_site2.models.enums.Newsletter;
import lombok.*;

import java.time.LocalDate;
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

    private List<GoodIdsRes> wishlist;

    private List<UserBagRes> bag;

    public static UserResDto mapFromEntity(User user) {
        List<GoodIdsRes> wishlist = new ArrayList<>();
        try{
            user.getWishlist().forEach(o-> wishlist.add(GoodIdsRes.mapFromEntity(o)));
        }catch (Exception e){}

        List<UserBagRes> bag = new ArrayList<>();
        try{
            user.getBag().forEach(o-> bag.add(UserBagRes.mapFromEntity(o)));
        }catch (Exception e){}

        UserResDto builder = UserResDto.builder()
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
                .wishlist(wishlist)
                .bag(bag)
                .build();
        return builder;
    }
}
