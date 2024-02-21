package com.aptech.eProject.repositories;

import com.aptech.eProject.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface CategoryRepository extends JpaRepository<Category, Integer> {
     Category findByName(String name);

     @Query("SELECT m FROM Category m WHERE m.name = :name")
     List<Category> findByCategoryName(@Param("name") String name);
}
