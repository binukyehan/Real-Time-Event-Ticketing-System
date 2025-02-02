package com.cw.event_ticketing_system.controller;

import com.cw.event_ticketing_system.dto.customer.*;
import com.cw.event_ticketing_system.entity.Customer;
import com.cw.event_ticketing_system.service.CustomerService;
import org.apache.coyote.Response;
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
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public CustomerResponseDTO createCustomer(@RequestBody CustomerDTO customerDTO){
        return customerService.create(customerDTO);
    }

    @PostMapping("/login")
    public CustomerResponseDTO login(@RequestBody CustomerLoginDTO customerLoginDTO){
        return customerService.login(customerLoginDTO);
    }

    @PutMapping("/change-password")
        public CustomerResponseDTO changePassword(@RequestBody CustomerChangePwDTO customerChangePwDTO){
        return customerService.changePassword(customerChangePwDTO);
    }

    @DeleteMapping("/delete")
    public CustomerResponseDTO deleteCustomerAcc(@RequestBody CustomerDeleteDTO customerDeleteDTO){
        return customerService.deleteCustomerAcc(customerDeleteDTO);
    }

    @PutMapping("/purchase-ticket")
    public ResponseEntity<String > purchaseTickets(@RequestBody CustomerTicketPurchaseDTO customerTicketPurchaseDTO){
        try {
            Callable<String> callable = () -> customerService.purchaseTickets(customerTicketPurchaseDTO);

            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<String> future = executor.submit(callable);

            String result = future.get();
            executor.shutdown();

            return ResponseEntity.ok(result);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while buying tickets.");
        }
    }



}
