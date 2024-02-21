package com.aptech.eProject.controllers.admin;

import com.aptech.eProject.models.*;
import com.aptech.eProject.services.OrderService;
import com.aptech.eProject.services.PaymentService;
import com.aptech.eProject.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin/orders")
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    ProductService productService;


    @GetMapping("")
    public ModelAndView index(ModelAndView model) {
        model.addObject("orders", orderService.getAll());
        model.addObject("payments", paymentService.getAll());
        model.setViewName("admin/order/index");
        return model;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(ModelAndView model, @PathVariable String id) {
        model.addObject("orders", orderService.findById(Integer.parseInt(id)));
        model.setViewName("admin/order/edit");
        return model;
    }

    @PostMapping("/edit/{id}")
    public String update(Model model, @PathVariable String id, @Valid Order order, BindingResult result) {
        if(result.hasErrors()) {
            model.addAttribute("orders", orderService.getAll());
            return "admin/orders/edit";
        }

        orderService.update(Integer.parseInt(id), order);
        return "redirect:/admin/orders";
    }

    @GetMapping("/search")
    public String searchByOrderStatus(@RequestParam(value = "orderStatus", required = false) EOrderStatus orderStatus, Model model) {
        List<Order> orders;
        if (orderStatus == null) {
            // Nếu không nhập gì, lấy tất cả đơn hàng
            orders = orderService.getAll();
        } else {
            // Nếu nhập từ khóa tìm kiếm, tìm đơn hàng theo trạng thái
            orders = orderService.searchByOrderStatus(orderStatus);
        }
        model.addAttribute("orders", orders);
        return "admin/order/index"; // Assuming the view name is "admin/order/index"
    }

    @GetMapping("/searchbyemail")
    public String searchOrdersByPaymentMethod(@RequestParam(value = "email", required = false) String email, Model model) {
        List<Order> orders ;
        if (email== null || email.isEmpty()) {
            // Nếu không nhập gì, lấy tất cả đơn hàng
            orders = orderService.getAll();
        } else {
            // Nếu nhập từ khóa tìm kiếm, tìm đơn hàng theo trạng thái
            orders = orderService.findByPayment(email);
        }
        model.addAttribute("orders", orders);
        return "admin/order/index"; // Assuming the view name is "admin/order/index"
    }
}
