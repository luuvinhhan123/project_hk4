package com.aptech.eProject.repositories;

import com.aptech.eProject.models.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface ProductSizeRepository extends JpaRepository<ProductSize, Integer> {
     ProductSize findByName(String name);

     @Query("SELECT m FROM ProductSize m WHERE m.name = :name")
     List<ProductSize> findByCategoryName(@Param("name") String name);
}
