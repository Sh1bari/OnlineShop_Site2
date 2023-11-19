package com.example.onlineshop_site2.models.entities;

import com.example.onlineshop_site2.models.dtos.responses.GoodIdsRes;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    private Integer amount;

}
