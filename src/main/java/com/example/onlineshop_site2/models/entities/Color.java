package com.example.onlineshop_site2.models.entities;

import com.example.onlineshop_site2.models.enums.ColorType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "colors")
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String code;
    @Enumerated(EnumType.STRING)
    private ColorType colorType;
    @OneToOne(mappedBy = "backColor", orphanRemoval = true)
    private Good backColorFor;


    @ManyToOne
    @JoinColumn(name = "good_id")
    private Good good;

    @OneToMany(mappedBy = "color", orphanRemoval = true)
    private List<Size> sizes;


}
