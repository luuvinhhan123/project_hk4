package com.aptech.eProject.controllers.admin;

import com.aptech.eProject.models.Category;
import com.aptech.eProject.models.EOrderStatus;
import com.aptech.eProject.models.Order;
import com.aptech.eProject.models.Product;
import com.aptech.eProject.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("")
    public ModelAndView index(ModelAndView model) {
        model.addObject("categories", categoryService.getAll());
        model.setViewName("admin/category/index");
        return model;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        categoryService.delete(Integer.parseInt(id));
        return "redirect:/admin/categories";
    }

    @GetMapping("/search")
    public String searchByOrderStatus(@RequestParam(value = "name", required = false) String name, Model model) {
        List<Category> categories;
        if (name == null|| name.isEmpty())  {
            // Nếu không nhập gì, lấy tất cả đơn hàng
            categories = categoryService.getAll();
        } else {
            // Nếu nhập từ khóa tìm kiếm, tìm đơn hàng theo trạng thái
            categories = categoryService.searchByCategory(name);
        }
        model.addAttribute("categories", categories);
        return "admin/category/index"; // Assuming the view name is "admin/order/index"
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(ModelAndView model, @PathVariable String id) {
        model.addObject("category", categoryService.findById(Integer.parseInt(id)));
        model.setViewName("admin/category/edit");
        return model;
    }

    @PostMapping("/edit/{id}")
    public String update(Model model, @PathVariable String id, @Valid Category category, BindingResult result) {
        Category detail =  categoryService.detail(Integer.parseInt(id));
        Category existingCategory = categoryService.findCategoryByName(category.getName());
        if (existingCategory != null && existingCategory.getName() != null
                && !existingCategory.getName().isEmpty()) {
            result.rejectValue("name", null,
                    "There is already an account registered with the same name");
        }
        if (result.hasErrors() || detail == null ) {
            model.addAttribute("category", category);
            result.rejectValue("name", null,
                    "Cannot not update try again");
            return "admin/category/edit";
        }

        detail.setId(category.getId());
        detail.setName(category.getName());
        categoryService.update(detail);
        return "redirect:/admin/categories";
    }

    @GetMapping("/create")
    public ModelAndView create(ModelAndView model) {
        model.addObject("category", new Category());
        model.setViewName("admin/category/create");
        return model;
    }

    @PostMapping("/create")
    public String createCategory(Model model, @Valid Category category, BindingResult result) {

        Category existingCategory = categoryService.findCategoryByName(category.getName());

        if (existingCategory != null && existingCategory.getName() != null
                && !existingCategory.getName().isEmpty()) {
            result.rejectValue("name", null,
                    "There is already an category registered with the same name");
        }
        if(result.hasErrors()) {
            model.addAttribute("category",category);
            return"admin/category/create";
        }

        categoryService.create(category);

        return "redirect:/admin/categories";
    }
}
