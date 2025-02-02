package com.cw.event_ticketing_system.controller;

import com.cw.event_ticketing_system.dto.vendor.*;
import com.cw.event_ticketing_system.service.VendorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RestController
@CrossOrigin(origins = "http://localhost:5173") //Allowing to connect with the frontend.
@RequestMapping("/api/vendor")
@Slf4j
public class VendorController {
    @Autowired
    private VendorService vendorService;

    @PostMapping("/register")
    public VendorResponseDTO register(@RequestBody VendorDTO vendorDTO){
        return vendorService.register(vendorDTO);
    }

    @PostMapping("/login")
    public VendorResponseDTO login(@RequestBody VendorLoginDTO vendorLoginDTO){
        return vendorService.login(vendorLoginDTO);
    }

    @PutMapping("/change-password")
    public VendorResponseDTO changePassword(@RequestBody VendorChangePwDTO vendorChangePwDTO){
        return vendorService.changePassword(vendorChangePwDTO);
    }

    @DeleteMapping("/delete")
    public VendorResponseDTO deleteVendorAcc(@RequestBody VendorDeleteDTO vendorDeleteDTO){
        return vendorService.deleteVendorAcc(vendorDeleteDTO);
    }

    @PutMapping("/issue-tickets")
    public ResponseEntity<String> issueTickets(@RequestBody VendorTicketIssueDTO vendorTicketIssueDTO){
        try {
            Callable<String > callable = () -> vendorService.issueTicket(vendorTicketIssueDTO);

            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<String> future = executor.submit(callable);

            String result = future.get();
            executor.shutdown();
        return ResponseEntity.ok(result);
    } catch (Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding tickets.");
    }
    }

}
