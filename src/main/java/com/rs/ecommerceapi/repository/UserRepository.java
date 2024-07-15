package com.rs.ecommerceapi.repository;

import com.rs.ecommerceapi.model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    public User findByEmail (String email);

    @Query("SELECT u FROM User u WHERE u.username = :username")
    public User findByUsername (@Param("username") String username);
}
