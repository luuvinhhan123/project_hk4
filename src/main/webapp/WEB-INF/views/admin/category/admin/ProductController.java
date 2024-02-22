package com.aptech.eProject.controllers.admin;

import com.aptech.eProject.models.EOrderStatus;
import com.aptech.eProject.models.Order;
import com.aptech.eProject.models.Product;
import com.aptech.eProject.models.ProductSize;
import com.aptech.eProject.services.*;
import org.springframework.ui.Model;
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
    ProductSizeService productSizeService;

    @Autowired
    ProductColorService productColorService;

    @Autowired
    SpeculateCategoryService speculateCategoryService;

    @GetMapping("")
    public ModelAndView index(ModelAndView model) {
        model.addObject("products", productService.getAll());
        model.setViewName("admin/product/index");
        return model;
    }

    @GetMapping("/search")
    public String searchByOrderStatus(@RequestParam(value = "title", required = false) String title, Model model) {
        List<Product> products;
        if (title == null) {
            // Nếu không nhập gì, lấy tất cả đơn hàng
            products = productService.getAll();
        } else {
            model.addAttribute("products", productService.getAll());
            // Nếu nhập từ khóa tìm kiếm, tìm đơn hàng theo trạng thái
            products = productService.searchByProductName(title);
        }
        model.addAttribute("products", products);
        return "admin/product/index"; // Assuming the view name is "admin/order/index"
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
        model.addObject("productsizes", productSizeService.getAll());
        model.addObject("productcolors", productColorService.getAll());
        model.setViewName("admin/product/create");
        return model;
    }

    @PostMapping("/create")
    public String createProduct(Model model, @Valid Product product, BindingResult result) {

        Product existingProduct = productService.findProductByTitleSizeAndColor(product.getTitle(), (long) product.getProductsize().getId(), (long) product.getProductcolor().getId());
        if (existingProduct != null) {
            model.addAttribute("categories", categoryservice.getAll());
            model.addAttribute("specailcates", speculateCategoryService.getAll());
            model.addAttribute("productsizes", productSizeService.getAll());
            model.addAttribute("productcolors", productColorService.getAll());
            result.rejectValue("title", null, "There is already a product registered with the same product");
            result.rejectValue("description", null, "There is already a product registered with the same product");
            return "admin/product/create";
        }

        if (result.hasErrors()) {
            model.addAttribute("categories", categoryservice.getAll());
            model.addAttribute("specailcates", speculateCategoryService.getAll());
            model.addAttribute("productsizes", productSizeService.getAll());
            model.addAttribute("productcolors", productColorService.getAll());
            return "admin/product/create";
        }

        productService.create(product);

        return "redirect:/admin/products";

    }}
