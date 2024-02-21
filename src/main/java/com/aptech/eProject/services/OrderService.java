package com.aptech.eProject.services;


import com.aptech.eProject.models.EOrderStatus;
import com.aptech.eProject.models.Order;
import com.aptech.eProject.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public List<Order> getAll(){
        return orderRepository.findAll();
    }

    public double sumTotalAmount() {
        return orderRepository.sumTotalAmount();
    }

    public int countFailed() {
        return orderRepository.countFailed();
    }

    public int countSUCCESS() {
        return orderRepository.countSUCCESS();
    }

    public int countPENDING() {
        return orderRepository.countPENDING();
    }

    public Order detail(Integer id) {
        return orderRepository.findById(id).get();
    }

    public Order update(Integer id, Order order) {
        try {
            Order exited = findById(id);
            if (exited != null) {
                order.setId(exited.getId());
                orderRepository.save(order);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return order;
    }
    public Order findById(int id) {
        return orderRepository.findById(id).get();
    }

    public List<Order> searchByOrderStatus(EOrderStatus orderStatus) {
        return orderRepository.searchByOrderStatus(orderStatus);
    }

/*    public List<Order> findByPayment(Payment payment) {
        return orderRepository.findByPayment(payment);
    }*/

    public List<Order> findByPayment(String email)
    {
        return orderRepository.findByPayment(email);
    }
}
