package com.rs.ecommerceapi.repository;

import com.rs.ecommerceapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    public User findByEmail(String email);
}
