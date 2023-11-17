package com.example.onlineshop_site2.models.entities;

import com.example.onlineshop_site2.models.enums.Newsletter;
import com.example.onlineshop_site2.models.enums.PhotoQuality;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "compressed_photos")
public class CompressedPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String path;

    @Enumerated(EnumType.STRING)
    private PhotoQuality photoQuality;

    @ManyToOne
    @JoinColumn(name = "photo_id")
    private Photo photo;

}
