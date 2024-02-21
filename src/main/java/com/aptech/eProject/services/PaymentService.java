package com.aptech.eProject.services;

import com.aptech.eProject.models.Payment;
import com.aptech.eProject.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    @Autowired
    PaymentRepository paymentRepository;

    public List<Payment> getAll(){
        return paymentRepository.findAll();
    }

}