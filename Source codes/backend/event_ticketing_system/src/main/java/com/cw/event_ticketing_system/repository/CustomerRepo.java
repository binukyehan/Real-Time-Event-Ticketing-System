package com.cw.event_ticketing_system.repository;

import com.cw.event_ticketing_system.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
    Customer findCustomerByUsername(String username);
}
