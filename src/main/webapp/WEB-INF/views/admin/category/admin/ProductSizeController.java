package com.aptech.eProject.controllers.admin;

import com.aptech.eProject.models.Category;
import com.aptech.eProject.models.ProductSize;
import com.aptech.eProject.models.User;
import com.aptech.eProject.services.CategoryService;
import com.aptech.eProject.services.ProductSizeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin/productsize")
public class ProductSizeController {
    @Autowired
    ProductSizeService productSizeService;

    @GetMapping("")
    public ModelAndView index(ModelAndView model) {
        model.addObject("productsizes", productSizeService.getAll());
        model.setViewName("admin/productsize/index");
        return model;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        productSizeService.delete(Integer.parseInt(id));
        return "redirect:/admin/productsize";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(ModelAndView model, @PathVariable String id) {
        model.addObject("productsize", productSizeService.findById(Integer.parseInt(id)));
        model.setViewName("admin/productsize/edit");
        return model;
    }

    @PostMapping("/edit/{id}")
    public String update(Model model, @PathVariable String id, @Valid ProductSize productsize, BindingResult result) {
        ProductSize detail =  productSizeService.detail(Integer.parseInt(id));
        ProductSize existingCategory = productSizeService.findProductSizeByName(productsize.getName());
        if (existingCategory != null && existingCategory.getName() != null
                && !existingCategory.getName().isEmpty()) {
            result.rejectValue("name", null,
                    "There is already an size registered with the same name");
        }
        if (result.hasErrors() || detail == null ) {
            model.addAttribute("productsize", productsize);
            result.rejectValue("name", null,
                    "Cannot not update try again");
            return "admin/productsize/edit";
        }

        detail.setId(productsize.getId());
        detail.setName(productsize.getName());
        productSizeService.update(detail);
        return "redirect:/admin/productsize";
    }

    @GetMapping("/create")
    public ModelAndView create(ModelAndView model) {
        model.addObject("productsize", new ProductSize());
        model.setViewName("admin/productsize/create");
        return model;
    }

    @PostMapping("/create")
    public String createProductSize(Model model, @Valid ProductSize productsize, BindingResult result) {

        ProductSize existingSize = productSizeService.findProductSizeByName(productsize.getName());

        if (existingSize != null) {
            result.rejectValue("name", null, "There is already a Size registered with the same name");
            model.addAttribute("productsize", productsize);
            return "admin/productsize/create"; // Trả về trang create để hiển thị lỗi
        }

        if(result.hasErrors()) {
            model.addAttribute("productsize", productsize);
            return "admin/productsize/create"; // Trả về trang create để hiển thị lỗi validation
        }

        productSizeService.create(productsize);

        return "redirect:/admin/productsize";
    }

}
