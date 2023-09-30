package com.example.onlineshop_site2.models.entities;


import com.example.onlineshop_site2.models.enums.SizeStatus;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sizes")
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double size;

    @Enumerated(EnumType.STRING)
    private SizeStatus sizeStatus;

    @ManyToOne
    @JoinColumn(name = "good_id")
    private Good good;

}
