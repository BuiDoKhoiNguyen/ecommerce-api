package com.rs.ecommerceapi.repository;

import com.rs.ecommerceapi.model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    public User findByEmail (String email);
}
