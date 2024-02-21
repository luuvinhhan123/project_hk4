package com.aptech.eProject.controllers.admin;

import com.aptech.eProject.services.CategoryService;
import com.aptech.eProject.services.OrderService;
import com.aptech.eProject.services.ProductService;
import com.aptech.eProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("admin/dashboard")
public class DashboardController {

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    OrderService orderService;

    @GetMapping("")
    public ModelAndView index(ModelAndView model) {
        long productCount = productService.countProducts();
        long userCount = userService.countUser();
        long categoryCount = categoryService.countCategories();
        int failedOrderCount = orderService.countFailed();
        int successOrderCount = orderService.countSUCCESS();
        int renderingCount = orderService.countPENDING();
        double totalOrderAmount = orderService.sumTotalAmount();

        model.addObject("productCount", productCount);
        model.addObject("userCount",userCount );
        model.addObject("categoryCount",categoryCount );
        model.addObject("failedOrderCount",failedOrderCount );
        model.addObject("successOrderCount",successOrderCount );
        model.addObject("renderingCount",renderingCount );
        model.addObject("totalOrderAmount", totalOrderAmount);
        model.setViewName("admin/dashboard/index");
        return model;
    }
}
