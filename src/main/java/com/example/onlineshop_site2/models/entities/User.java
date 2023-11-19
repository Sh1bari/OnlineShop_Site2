package com.example.onlineshop_site2.models.entities;

import com.example.onlineshop_site2.models.enums.Gender;
import com.example.onlineshop_site2.models.enums.Newsletter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate DOB;

    private String country;

    private String state;

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"))
    private List<Role> roles;

    private String city;

    private String phone;

    private String password;

    @Enumerated(EnumType.STRING)
    private Newsletter newsletter;
}
