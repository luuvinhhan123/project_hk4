package com.aptech.eProject.models;


import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="tbl_profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name = "birthday")
    private Date birthday;

    @NotEmpty(message="Place of birth should be not blank")
    @Column(name = "place_of_birth", length = 255)
    private String placeOfBirth;

    @NotEmpty(message="Address should be not blank")
    @Column(name = "address")
    private String address;

    @Column(name = "sex")
    private boolean sex;

    @OneToOne(mappedBy = "profile")
    private User user;


}