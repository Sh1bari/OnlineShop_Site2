package com.example.onlineshop_site2.models.entities;

import com.example.onlineshop_site2.models.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "application", orphanRemoval = true)
    private List<GoodItem> goodItems;

    private String clientComment;

    private String adminComment;

    @Basic
    private LocalDateTime time;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;


}
