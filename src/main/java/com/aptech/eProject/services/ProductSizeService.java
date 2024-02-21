package com.aptech.eProject.services;

import com.aptech.eProject.models.ProductSize;
import com.aptech.eProject.repositories.ProductSizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSizeService {
    @Autowired
    ProductSizeRepository productSizeRepository;

    public List<ProductSize> getAll(){
        return productSizeRepository.findAll();
    }
    public ProductSize findProductSizeByName(String name) {
        return productSizeRepository.findByName(name);
    }

    public boolean update(ProductSize productSize) {
        try {
            productSizeRepository.save(productSize);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
    public long countCategories(){
        return productSizeRepository.count();
    }

    public ProductSize create(ProductSize productSize) {
        try {
            productSizeRepository.save(productSize);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return productSize;
    }

    public ProductSize detail(Integer id) {
        return productSizeRepository.findById(id).get();
    }

    public ProductSize findById(int id) {
        return productSizeRepository.findById(id).get();
    }

    public List<ProductSize> searchByProductSize(String name) {
        return productSizeRepository.findByCategoryName(name);
    }

    public boolean delete(Integer productsizeId) {
        try {
            productSizeRepository.deleteById(productsizeId);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}