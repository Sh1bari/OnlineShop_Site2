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
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true)
    @JoinColumn(name = "application_id")
    private Application application;

    private String amount;

    private String currency;

    private String status;

    private String billId;

}
