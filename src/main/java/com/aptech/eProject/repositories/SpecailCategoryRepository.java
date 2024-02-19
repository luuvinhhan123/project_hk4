package com.aptech.eProject.repositories;

import com.aptech.eProject.models.Category;
import com.aptech.eProject.models.SpecialCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpecailCategoryRepository  extends JpaRepository<SpecialCategory, Integer> {
    SpecialCategory findByName(String name);

    @Query("SELECT m FROM SpecialCategory m WHERE m.name = :name")
    List<SpecialCategory> findBySpecailName(@Param("name") String name);
}
