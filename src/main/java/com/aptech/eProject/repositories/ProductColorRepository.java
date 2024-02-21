package com.aptech.eProject.repositories;

import com.aptech.eProject.models.ProductColor;
import com.aptech.eProject.models.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface ProductColorRepository extends JpaRepository<ProductColor, Integer> {
     ProductColor findByName(String name);

     @Query("SELECT m FROM ProductColor m WHERE m.name = :name")
     List<ProductColor> findByCategoryName(@Param("name") String name);
}
