package com.aptech.eProject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "producsize")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = true)
    private int id;

    @NotBlank
    @Size(min = 1, max = 10, message = "Size must be between 1 and 10 characters length")
    @Column(nullable = true)
    private String name;

    @OneToMany(mappedBy = "productsize", fetch = FetchType.EAGER)
    private List<Product> productsize;
}