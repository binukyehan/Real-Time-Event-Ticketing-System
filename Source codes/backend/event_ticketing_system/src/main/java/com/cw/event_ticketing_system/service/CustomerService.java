package com.cw.event_ticketing_system.service;

import com.cw.event_ticketing_system.dto.customer.*;
import com.cw.event_ticketing_system.entity.Customer;
import com.cw.event_ticketing_system.repository.CustomerRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@Slf4j
public class CustomerService {
    private TicketPool ticketPool;
    private CustomerRepo customerRepo;


    @Autowired
    public CustomerService(TicketPool ticketPool, CustomerRepo customerRepo) {
        this.ticketPool = ticketPool;
        this.customerRepo = customerRepo;
    }

    public CustomerResponseDTO create(CustomerDTO customerDTO){
        CustomerResponseDTO response = new CustomerResponseDTO();
        try {
            Customer customer = customerRepo.findCustomerByUsername(customerDTO.getUsername());
            if (customer == null) {
                Customer newCustomer = new Customer();
                newCustomer.setName(customerDTO.getName());
                newCustomer.setEmail(customerDTO.getEmail());
                newCustomer.setUsername(customerDTO.getUsername());
                newCustomer.setPassword(customerDTO.getPassword());
                customerRepo.save(newCustomer);
                log.info(newCustomer.getUsername() + " created a customer profile successfully.");
                response.setMessage("Customer profile created successfully");
                response.setSuccess(true);
                return response;
            } else {
                response.setMessage("Customer already exists in the system.");
                return response;
            }
        } catch (Exception e){
            log.error("An exception occurred while creating the customer profile: " + e.getMessage());
            response.setMessage("Error occurred while creating your account.");
            return response;
        }
    }

    public CustomerResponseDTO login(CustomerLoginDTO customerLoginDTO){
        CustomerResponseDTO response = new CustomerResponseDTO();
        try{
            Customer customer = customerRepo.findCustomerByUsername(customerLoginDTO.getUsername());
            if (customer != null){
                if(customer.getPassword().equals(customerLoginDTO.getPassword())){
                    log.info(customer.getUsername() + " customer logged in successfully.");
                    response.setMessage("Login successful.");
                    response.setSuccess(true);
                    return response;
                } else {
                    log.info(customer.getUsername() + " customer logged in failed due to incorrect password.");
                    response.setMessage("incorrect password.");
                    return response;
                }
            } else {
                response.setMessage("Customer does not exist.");
                return response;
            }

        }catch (Exception e){
            log.error("An exception occurred while " + customerLoginDTO.getUsername() + " was logging in: " + e.getMessage());
            response.setMessage("An error occurred while logging in");
            return response;
        }
    }

    public CustomerResponseDTO changePassword(CustomerChangePwDTO customerChangePwDTO){
        CustomerResponseDTO response = new CustomerResponseDTO();
        try {
            if (!customerChangePwDTO.getCurrentPassword().equals(customerChangePwDTO.getNewPassword())) {
                Customer customer = customerRepo.findCustomerByUsername(customerChangePwDTO.getUsername());
                if (customer != null) {
                    if (customer.getPassword().equals(customerChangePwDTO.getCurrentPassword())) {
                        customer.setPassword(customerChangePwDTO.getNewPassword());
                        customerRepo.save(customer);
                        log.info("Vendor " + customer.getUsername() + " has changed password successfully.");
                        response.setMessage("Password changed successfully");
                        response.setSuccess(true);
                        return response;
                    } else {
                        response.setMessage("Incorrect current password.");
                        return response;
                    }
                } else {
                    response.setMessage("Cannot find customer " + customerChangePwDTO.getUsername() + ".");
                    return response;
                }
            } else {
                response.setMessage("The current password and the new password cannot be the same.");
                return response;
            }
        } catch (Exception e){
            log.error("An exception occurred while " + customerChangePwDTO.getUsername() + "was changing password.");
            response.setMessage("An error occurred while changing password.");
            return response;
        }
    }

    public CustomerResponseDTO deleteCustomerAcc(CustomerDeleteDTO customerDeleteDTO){
        CustomerResponseDTO response = new CustomerResponseDTO();
        try {
            Customer customer = customerRepo.findCustomerByUsername(customerDeleteDTO.getUsername());
            if (customer != null) {
                if (customer.getPassword().equals(customerDeleteDTO.getPassword())) {
                    if (customerDeleteDTO.getConfirmation().equals("DELETE")) {
                        customerRepo.delete(customer);
                        log.info("Customer " + customerDeleteDTO.getUsername() + "'s account deleted successfully.");
                        response.setMessage("Account deleted successfully");
                        response.setSuccess(true);
                        return response;
                    } else {
                        response.setMessage("Confirmation is incorrect, properly type 'DELETE' to delete your account.");
                        return response;
                    }
                } else {
                    log.info("Password entered by " + customer.getUsername() + " is incorrect.");
                    response.setMessage("Password incorrect.");
                    return response;
                }
            } else {
                response.setMessage("The customer does not exist.");
                return response;
            }
        } catch (Exception e){
            log.error("An exception occurred while deleting " + customerDeleteDTO.getUsername() + " customer: " + e.getMessage());
            response.setMessage("An error occurred while deleting the account.");
            return response;
        }

    }

    public String purchaseTickets(CustomerTicketPurchaseDTO customerTicketPurchaseDTO){
        synchronized (this) {
            try {
                Customer customer = customerRepo.findCustomerByUsername(customerTicketPurchaseDTO.getName());
                if (customer != null) {
                    String response = ticketPool.removeTickets(customerTicketPurchaseDTO.getNumberOfTickets(), customerTicketPurchaseDTO.getEventId());
                    log.info("Customer " + customer.getUsername() + ": " + response);
                    return response;
                } else {
                    return "Customer not found.";
                }
            } catch (Exception e) {
                log.error("An exception occurred while buying tickets by Customer " + customerTicketPurchaseDTO.getName() + ": " + e.getMessage());
                return "An error occurred while buying tickets.";
            }
        }
    }
}
