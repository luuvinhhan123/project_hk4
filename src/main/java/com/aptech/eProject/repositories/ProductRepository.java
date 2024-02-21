package com.aptech.eProject.repositories;

import com.aptech.eProject.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findByTitle(String title);

    @Query("SELECT m FROM Product m WHERE m.title LIKE :title")
    List<Product> findListByProductName(@Param("title") String title);


}
