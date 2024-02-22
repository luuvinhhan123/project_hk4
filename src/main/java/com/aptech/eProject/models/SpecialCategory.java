package com.aptech.eProject.models;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "special_categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpecialCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotBlank
    @Size(min = 3, max = 100, message = "SpecialCategory title must be between 5 and 100 characters length")
    @Column(nullable = true)
    private String name;

    @OneToMany(mappedBy = "specialCategory", fetch = FetchType.EAGER)
    private List<Product> product;
}
