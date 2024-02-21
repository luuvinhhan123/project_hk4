package com.aptech.eProject.services;

import com.aptech.eProject.models.EOrderStatus;
import com.aptech.eProject.models.Order;
import com.aptech.eProject.models.Product;
import com.aptech.eProject.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public List<Product> getAll(){
        return productRepository.findAll();
    }

    public Product findProductByTitle(String title)
    {
        return productRepository.findByTitle(title);
    }

    public List<Product> searchByProductName(String title) {
        // Thêm ký tự đại diện '%' trước và sau tiêu đề để tìm kiếm một phần của tiêu đề
        String searchTitle = "%" + title + "%";
        return productRepository.findListByProductName(searchTitle);
    }


    public Product findById(int id) {
        return productRepository.findById(id).get();
    }

    public long countProducts() {
        return productRepository.count(); // Sử dụng phương thức count() của JpaRepository để đếm tổng số lượng sản phẩm
    }
    public Product update(Integer id, Product product) {
        try {
            Product exited = findById(id);
            if (exited != null) {
                product.setId(exited.getId());
                productRepository.save(product);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return product;
    }


    public Product create(Product product) {
        try {
            productRepository.save(product);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return product;
    }

    public Product detail(Integer id) {
        return productRepository.findById(id).get();
    }

    public boolean delete(Integer categoryId) {
        try {
            productRepository.deleteById(categoryId);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
