package com.aptech.eProject.services;

import com.aptech.eProject.models.Category;
import com.aptech.eProject.models.Product;
import com.aptech.eProject.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> getAll(){
        return categoryRepository.findAll();
    }

    public Category findCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    public boolean update(Category category) {
        try {
            categoryRepository.save(category);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
    public long countCategories(){
        return categoryRepository.count();
    }

    public Category create(Category category) {
        try {
            categoryRepository.save(category);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return category;
    }

    public Category detail(Integer id) {
        return categoryRepository.findById(id).get();
    }

    public Category findById(int id) {
        return categoryRepository.findById(id).get();
    }

    public boolean delete(Integer categoryId) {
        try {
            categoryRepository.deleteById(categoryId);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}