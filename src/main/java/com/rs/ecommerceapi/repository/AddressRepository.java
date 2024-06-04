package com.rs.ecommerceapi.repository;

import com.rs.ecommerceapi.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
