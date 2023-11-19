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

    @ManyToOne
    @JoinColumn(name = "good_id")
    private Good good;

    @OneToMany(mappedBy = "color", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<Size> sizes;


    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "good_back_color_id")
    private Good goodBackColor;

}
