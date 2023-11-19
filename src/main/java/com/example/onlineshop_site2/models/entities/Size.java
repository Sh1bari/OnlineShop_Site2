package com.example.onlineshop_site2.models.entities;


import com.example.onlineshop_site2.models.enums.SizeStatus;
import com.example.onlineshop_site2.models.enums.SizeType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
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

    @Enumerated(EnumType.STRING)
    private SizeType sizeType;

    @ManyToOne
    @JoinColumn(name = "good_id")
    private Good good;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

}
