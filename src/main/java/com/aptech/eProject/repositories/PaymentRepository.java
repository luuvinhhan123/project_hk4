package com.aptech.eProject.repositories;

import com.aptech.eProject.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


@Component
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
