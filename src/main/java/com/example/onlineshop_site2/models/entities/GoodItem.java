package com.example.onlineshop_site2.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "good_id")
    private Good good;

    private String colorName;

    private String colorCode;

    private String size;

    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

}
