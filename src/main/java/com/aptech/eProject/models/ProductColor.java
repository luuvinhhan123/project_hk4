package com.aptech.eProject.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "productcolor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductColor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private int id;

    @Column(nullable = true)
    private String name;

    @OneToMany(mappedBy = "productcolor", fetch = FetchType.EAGER)
    private List<Product> productcolor;
}
