package com.example.onlineshop_site2.models.dtos.requests;

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
public class UpdateUserReq {

    private String firstName;

    private String lastName;

    private Gender gender;

    private LocalDate DOB;

    private String country;

    private String state;
    private String city;

    private String phone;

    private Newsletter newsletter;

    public User mapToEntity(User existingUser) {
        existingUser.setFirstName(this.firstName);
        existingUser.setLastName(this.lastName);
        existingUser.setGender(this.gender);
        existingUser.setDOB(this.DOB);
        existingUser.setCountry(this.country);
        existingUser.setState(this.state);
        existingUser.setCity(this.city);
        existingUser.setPhone(this.phone);
        existingUser.setNewsletter(this.newsletter);

        return existingUser;
    }
}
