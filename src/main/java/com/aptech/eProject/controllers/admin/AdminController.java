package com.aptech.eProject.controllers.admin;

import com.aptech.eProject.models.Role;
import com.aptech.eProject.models.User;
import com.aptech.eProject.services.ProductService;
import com.aptech.eProject.services.RoleService;
import com.aptech.eProject.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    ProductService productService;

    @GetMapping("")
    public ModelAndView index(ModelAndView model) {
        long productCount = productService.countProducts();
        model.addObject("users", userService.getAll());
        model.addObject("productCount", productCount);
        model.setViewName("admin/usermanager/index");
        return model;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(ModelAndView model, @PathVariable String id) {
        model.addObject("user", userService.findUserById(Integer.parseInt(id)));
        model.addObject("roles", roleService.getAll());
        model.setViewName("admin/usermanager/edit");
        return model;
    }

    @PostMapping("/edit/{id}")
    public String update(Model model, @PathVariable String id, @Valid User user, BindingResult result) {
        User existingUser = userService.findUserById(Integer.parseInt(id));
        if (existingUser != null && !existingUser.getEmail().equals(user.getEmail())) {
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }
        if (result.hasErrors()) {
            List<Role> roles = roleService.getAll();
            model.addAttribute("roles", roles);
            model.addAttribute("user", user);
            return "admin/usermanager/edit";
        }
        assert existingUser != null;
        existingUser.setFirstname(user.getFirstname());
        existingUser.setLastname(user.getLastname());
        existingUser.setRoles(user.getRoles());
        existingUser.setProfile(user.getProfile());

        userService.updateUser(existingUser);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        userService.delete(Integer.parseInt(id));
        return "redirect:/admin";
    }
}

