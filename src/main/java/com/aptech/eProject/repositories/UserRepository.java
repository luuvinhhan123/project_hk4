package com.aptech.eProject.repositories;

import com.aptech.eProject.models.Order;
import com.aptech.eProject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserRepository extends JpaRepository<User, Integer> {
	User findByEmail(String email);
	@Query("SELECT m FROM User m WHERE m.email = :email")
	List<User> searchByEmail(@Param("email") String email);

}
