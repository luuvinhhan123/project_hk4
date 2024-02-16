package com.aptech.eProject.controllers.admin;

import com.aptech.eProject.models.Category;
import com.aptech.eProject.models.Product;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
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

    @GetMapping("/search")
    public String search(Model model, @RequestParam(value = "email", required = false) String email) {
        if (email == null || email.isEmpty()) {
            // Nếu không nhập gì, lấy tất cả sản phẩm
            List<User> allUsers = userService.getAll();
            model.addAttribute("users", allUsers);
        } else {
            // Nếu nhập từ khóa tìm kiếm, tìm sản phẩm theo tiêu đề
            User searchEmail = userService.findUserByEmail(email);
            if (searchEmail != null) {
                // Nếu sản phẩm được tìm thấy, thêm nó vào model để hiển thị trên trang
                model.addAttribute("users", Collections.singletonList(searchEmail));
            } else {
                // Nếu không tìm thấy sản phẩm, thông báo cho người dùng
                model.addAttribute("message", "No user found with the email: " + email);
            }
        }
        // Trả về trang hiển thị danh sách sản phẩm
        return "admin/usermanager/index";
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

    @GetMapping("/create")
    public ModelAndView create(ModelAndView model) {
        model.addObject("users", new User());
        model.addObject("role", roleService.getAll());
        model.setViewName("admin/usermanager/create");
        return model;
    }

    @PostMapping("/create")
    public String createCategory(Model model, @Valid User user, BindingResult result) {

        User existingEmail = userService.findUserByEmail(user.getEmail());
        model.addAttribute("users", new User());
        model.addAttribute("roles", roleService.getAll());
        if (existingEmail != null && existingEmail.getEmail() != null
                && !existingEmail.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "There is already an email registered with the same name");
        }
        if(result.hasErrors()) {
            model.addAttribute("user",user);
            return"admin/usermanager/create";
        }

        userService.createUser(user);

        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        userService.delete(Integer.parseInt(id));
        return "redirect:/admin";
    }

}

