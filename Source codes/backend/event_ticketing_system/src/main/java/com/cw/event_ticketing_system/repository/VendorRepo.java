package com.cw.event_ticketing_system.repository;

import com.cw.event_ticketing_system.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepo extends JpaRepository<Vendor, Long> {
    Vendor findVendorByUsername(String username);
}
