package com.aptech.eProject.services;

import com.aptech.eProject.models.ProductColor;
import com.aptech.eProject.repositories.ProductColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductColorService {
    @Autowired
    ProductColorRepository productColorRepository;

    public List<ProductColor> getAll(){
        return productColorRepository.findAll();
    }
    public ProductColor findProductColorByName(String name) {
        return productColorRepository.findByName(name);
    }

    public boolean update(ProductColor productColor) {
        try {
            productColorRepository.save(productColor);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
    public long countCategories(){
        return productColorRepository.count();
    }

    public ProductColor create(ProductColor productColor) {
        try {
            productColorRepository.save(productColor);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return productColor;
    }

    public ProductColor detail(Integer id) {
        return productColorRepository.findById(id).get();
    }

    public ProductColor findById(int id) {
        return productColorRepository.findById(id).get();
    }

    public List<ProductColor> searchByProductColorSize(String name) {
        return productColorRepository.findByCategoryName(name);
    }

    public boolean delete(Integer productcolorId) {
        try {
            productColorRepository.deleteById(productcolorId);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}