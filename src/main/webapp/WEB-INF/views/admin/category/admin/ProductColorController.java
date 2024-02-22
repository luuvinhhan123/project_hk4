package com.aptech.eProject.controllers.admin;

import com.aptech.eProject.models.ProductColor;
import com.aptech.eProject.services.ProductColorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin/productcolor")
public class ProductColorController {
    @Autowired
    ProductColorService productColorService;

    @GetMapping("")
    public ModelAndView index(ModelAndView model) {
        model.addObject("productcolor", productColorService.getAll());
        model.setViewName("admin/productcolor/index");
        return model;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        productColorService.delete(Integer.parseInt(id));
        return "redirect:/admin/productcolor";
    }

    @GetMapping("/search")
    public String searchByOrderStatus(@RequestParam(value = "name", required = false) String name, Model model) {
        List<ProductColor> productColors;
        if (name == null || name.isEmpty()) {
            // Nếu không nhập gì, lấy tất cả đơn hàng
            productColors = productColorService.getAll();
        } else {
            // Nếu nhập từ khóa tìm kiếm, tìm đơn hàng theo trạng thái
            productColors = productColorService.searchByProductColorSize(name);
        }
        model.addAttribute("productcolors", productColors);
        return "admin/productcolor/index"; // Assuming the view name is "admin/order/index"
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(ModelAndView model, @PathVariable String id) {
        model.addObject("productcolor", productColorService.findById(Integer.parseInt(id)));
        model.setViewName("admin/productcolor/edit");
        return model;
    }

    @PostMapping("/edit/{id}")
    public String update(Model model, @PathVariable String id, @Valid ProductColor productcolor, BindingResult result) {
        ProductColor detail = productColorService.detail(Integer.parseInt(id));
        ProductColor existingCategory = productColorService.findProductColorByName(productcolor.getName());
        if (existingCategory != null && existingCategory.getName() != null
                && !existingCategory.getName().isEmpty()) {
            result.rejectValue("name", null,
                    "There is already an account registered with the same name");
        }
        if (result.hasErrors() || detail == null) {
            model.addAttribute("productcolor", productcolor);
            result.rejectValue("name", null,
                    "Cannot not update try again");
            return "admin/productcolor/edit";
        }

        detail.setId(productcolor.getId());
        detail.setName(productcolor.getName());
        productColorService.update(detail);
        return "redirect:/admin/productcolor";
    }

    @GetMapping("/create")
    public ModelAndView create(ModelAndView model) {
        model.addObject("preproductions", new ProductColor());
        model.setViewName("admin/productcolor/create");
        return model;
    }

    @PostMapping("/create")
    public String createCategory(Model model, @Valid ProductColor productcolor, BindingResult result) {

        ProductColor existingCategory = productColorService.findProductColorByName(productcolor.getName());

        if (existingCategory != null && existingCategory.getName() != null
                && !existingCategory.getName().isEmpty()) {
            result.rejectValue("name", null,
                    "There is already an color registered with the same name");
        }
        if (result.hasErrors()) {
            model.addAttribute("preproductions", productcolor);
            return "admin/productcolor/create";
        }

        productColorService.create(productcolor);

        return "redirect:/admin/productcolor";
    }
}
