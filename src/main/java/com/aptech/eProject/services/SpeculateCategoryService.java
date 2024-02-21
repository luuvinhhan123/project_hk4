package com.aptech.eProject.services;

import com.aptech.eProject.models.SpecialCategory;
import com.aptech.eProject.repositories.SpecailCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpeculateCategoryService {
    @Autowired
    SpecailCategoryRepository specailCategoryRepository;

    public List<SpecialCategory> getAll(){
        return specailCategoryRepository.findAll();
    }

    public SpecialCategory finSpecialCategory(String name) {
        return specailCategoryRepository.findByName(name);
    }

    public boolean update(SpecialCategory specialCategory) {
        try {
            specailCategoryRepository.save(specialCategory);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    public SpecialCategory create(SpecialCategory specialCategory) {
        try {
            specailCategoryRepository.save(specialCategory);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return specialCategory;
    }

    public SpecialCategory detail(Integer id) {
        return specailCategoryRepository.findById(id).get();
    }

    public SpecialCategory findById(int id) {
        return specailCategoryRepository.findById(id).get();
    }

    public List<SpecialCategory> searchByCategory(String name) {
        return specailCategoryRepository.findBySpecailName(name);
    }

    public boolean delete(Integer id) {
        try {
            specailCategoryRepository.deleteById(id);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}