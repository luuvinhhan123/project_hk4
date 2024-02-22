package com.aptech.eProject.controllers.admin;
import com.aptech.eProject.models.SpecialCategory;
import com.aptech.eProject.services.SpeculateCategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin/categorisations")
public class SpecailCategoryController {
    @Autowired
    SpeculateCategoryService speculateCategoryService;

    @GetMapping("")
    public ModelAndView index(ModelAndView model) {
        model.addObject("speculates", speculateCategoryService.getAll());
        model.setViewName("admin/speculate/index");
        return model;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        speculateCategoryService.delete(Integer.parseInt(id));
        return "redirect:/admin/categorisations";
    }

    @GetMapping("/search")
    public String searchByOrderStatus(@RequestParam(value = "name", required = false) String name, Model model) {
        List<SpecialCategory> speculates;
        if (name == null|| name.isEmpty())  {
            // Nếu không nhập gì, lấy tất cả đơn hàng
            speculates = speculateCategoryService.getAll();
        } else {
            // Nếu nhập từ khóa tìm kiếm, tìm đơn hàng theo trạng thái
            speculates = speculateCategoryService.searchByCategory(name);
        }
        model.addAttribute("specialCategory", speculates);
        return "admin/speculate/index"; // Assuming the view name is "admin/order/index"
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(ModelAndView model, @PathVariable String id) {
        model.addObject("specialCategory", speculateCategoryService.findById(Integer.parseInt(id)));
        model.setViewName("admin/speculate/edit");
        return model;
    }

    @PostMapping("/edit/{id}")
    public String update(Model model, @PathVariable String id, @Valid SpecialCategory specialCategory, BindingResult result) {
        SpecialCategory detail =  speculateCategoryService.detail(Integer.parseInt(id));
        SpecialCategory existingCategory = speculateCategoryService.finSpecialCategory(specialCategory.getName());
        if (existingCategory != null && existingCategory.getName() != null
                && !existingCategory.getName().isEmpty()) {
            result.rejectValue("name", null,
                    "There is already an special name registered with the same name");
        }
        if (result.hasErrors() || detail == null ) {
            model.addAttribute("specialCategory", specialCategory);
            return "admin/speculate/edit";
        }

        detail.setId(specialCategory.getId());
        detail.setName(specialCategory.getName());
        speculateCategoryService.update(detail);
        return "redirect:/admin/categorisations";
    }


    @GetMapping("/create")
    public ModelAndView create(ModelAndView model) {
        model.addObject("specialCategory", new SpecialCategory());
        model.setViewName("admin/speculate/create");
        return model;
    }

    @PostMapping("/create")
    public String createSpecialCategory(Model model, @Valid SpecialCategory specialCategory, BindingResult result) {
        SpecialCategory existingCategory = speculateCategoryService.finSpecialCategory(specialCategory.getName());

        if (existingCategory != null && existingCategory.getName() != null
                && existingCategory.getName().equals(specialCategory.getName())) {
            result.rejectValue("name", null, "There is already a special category registered with the same");
            model.addAttribute("specialCategory", specialCategory);
            return "admin/speculate/create";
        }

        if (result.hasErrors()) {
            model.addAttribute("specialCategory", specialCategory);
            return "admin/speculate/create";
        }

        speculateCategoryService.create(specialCategory);

        return "redirect:/admin/categorisations";
    }
}
