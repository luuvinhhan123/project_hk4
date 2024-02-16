package com.aptech.eProject.controllers.admin;

import com.aptech.eProject.models.Category;
import com.aptech.eProject.models.SpecialCategory;
import com.aptech.eProject.services.CategoryService;
import com.aptech.eProject.services.SpeculateCategoryService;
import org.springframework.ui.Model;
import com.aptech.eProject.models.Product;
import com.aptech.eProject.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin/products")
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryservice;

    @Autowired
    SpeculateCategoryService speculateCategoryService;

    @GetMapping("")
    public ModelAndView index(ModelAndView model) {
        model.addObject("products", productService.getAll());
        model.setViewName("admin/product/index");
        return model;
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(value = "title", required = false) String title) {
        if (title == null || title.isEmpty()) {
            // Nếu không nhập gì, lấy tất cả sản phẩm
            List<Product> allProducts = productService.getAll();
            model.addAttribute("products", allProducts);
        } else {
            // Nếu nhập từ khóa tìm kiếm, tìm sản phẩm theo tiêu đề
            Product searchProduct = productService.findProductByTitle(title);
            if (searchProduct != null) {
                // Nếu sản phẩm được tìm thấy, thêm nó vào model để hiển thị trên trang
                model.addAttribute("products", Collections.singletonList(searchProduct));
            } else {
                // Nếu không tìm thấy sản phẩm, thông báo cho người dùng
                model.addAttribute("message", "No product found with the title: " + title);
            }
        }
        // Trả về trang hiển thị danh sách sản phẩm
        return "admin/product/index";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        productService.delete(Integer.parseInt(id));
        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(ModelAndView model, @PathVariable String id) {
        model.addObject("categories", categoryservice.getAll());
        model.addObject("product", productService.findById(Integer.parseInt(id)));
        model.addObject("specailcates", speculateCategoryService.getAll());
        model.setViewName("admin/product/edit");
        return model;
    }

    @PostMapping("/edit/{id}")
    public String update(Model model, @PathVariable String id, @Valid Product product, BindingResult result) {

        Product existingProduct = productService.findProductByTitle(product.getTitle());
        if(result.hasErrors()) {
            model.addAttribute("categories", categoryservice.getAll());
            model.addAttribute("specailcates", speculateCategoryService.getAll());
            return "admin/product/edit";
        }
        if (existingProduct != null && existingProduct.getTitle() != null && !existingProduct.getTitle().isEmpty()) {
            model.addAttribute("specailcates", speculateCategoryService.getAll());
            model.addAttribute("categories", categoryservice.getAll());
            result.rejectValue("title",null,  "There is already a product registered with the same name");
            return "admin/product/edit";
        }

        productService.update(Integer.parseInt(id), product);
        return "redirect:/admin/products";
    }

    @GetMapping("/create")
    public ModelAndView create(ModelAndView model) {
        model.addObject("product", new Product());
        model.addObject("categories", categoryservice.getAll());
        model.addObject("specailcates", speculateCategoryService.getAll());
        model.setViewName("admin/product/create");
        return model;
    }

    @PostMapping("/create")
    public String createProduct(Model model, @Valid Product product, BindingResult result) {

        Product existingProduct = productService.findProductByTitle(product.getTitle());
        if (existingProduct != null && existingProduct.getTitle() != null && !existingProduct.getTitle().isEmpty()) {
            model.addAttribute("categories", categoryservice.getAll());
            model.addAttribute("specailcates", speculateCategoryService.getAll());
            result.rejectValue("title",null,  "There is already a product registered with the same name");
            return "admin/product/create";
        }   if(result.hasErrors()) {
            model.addAttribute("categories", categoryservice.getAll());
            model.addAttribute("specailcates", speculateCategoryService.getAll());
            return "admin/product/create";
        }

        productService.create(product);

        return "redirect:/admin/products";
    }
}
