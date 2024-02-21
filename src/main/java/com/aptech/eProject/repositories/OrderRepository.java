package com.aptech.eProject.repositories;

;
import com.aptech.eProject.models.EOrderStatus;
import com.aptech.eProject.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("SELECT SUM(o.totalAmount) FROM Order o")
    double sumTotalAmount();

    @Query("SELECT COUNT(o) FROM Order o WHERE o.orderStatus = 'FAILED'")
    int countFailed();

    @Query("SELECT COUNT(o) FROM Order o WHERE o.orderStatus = 'SUCCESS'")
    int countSUCCESS();

    @Query("SELECT COUNT(o) FROM Order o WHERE o.orderStatus = 'PENDING'")
    int countPENDING();

    @Query("SELECT m FROM Order m WHERE m.orderStatus = :orderStatus")
    List<Order> searchByOrderStatus(@Param("orderStatus") EOrderStatus orderStatus);

   /* @Query("SELECT m FROM Order m WHERE m.payment = :paymentMethod")
    List<Order> findByPayment(@Param("paymentMethod") Payment paymentMethod);*/

    @Query("SELECT m FROM Order m WHERE m.email = :email")
    List<Order> findByPayment(@Param("email") String email);

}
