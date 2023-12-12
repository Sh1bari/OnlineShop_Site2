package com.example.onlineshop_site2.models.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FirstPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @OneToOne
    @JoinColumn(name = "good_id")
    private Good good;

    @OneToOne
    @JoinColumn(name = "photo_id")
    private Photo photo;
}
