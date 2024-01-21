package com.example.onlineshop_site2.models.entities;

import jakarta.persistence.*;
import lombok.*;


/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Entity
@Table(name = "user_bag")
@Getter
@Setter
public class UserBag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "good_id")
    private Good good;

    @OneToOne
    @JoinColumn(name = "size_id")
    private Size size;

    private Integer amount;

}
