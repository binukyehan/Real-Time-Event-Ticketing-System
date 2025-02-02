package com.cw.event_ticketing_system.service;

import com.cw.event_ticketing_system.dto.vendor.*;
import com.cw.event_ticketing_system.entity.Vendor;
import com.cw.event_ticketing_system.repository.VendorRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@Slf4j
public class VendorService {
    private TicketPool ticketPool;
    private VendorRepo vendorRepo;


    @Autowired
    public VendorService(TicketPool ticketPool, VendorRepo vendorRepo) {
        this.ticketPool = ticketPool;
        this.vendorRepo = vendorRepo;
    }

    public VendorResponseDTO register(VendorDTO vendorDTO){ //Create
        Vendor vendor = vendorRepo.findVendorByUsername(vendorDTO.getUsername());
        VendorResponseDTO response = new VendorResponseDTO();
        try {
            if (vendor == null) {
                Vendor newVendor = new Vendor();
                newVendor.setName(vendorDTO.getName());
                newVendor.setEmail(vendorDTO.getEmail());
                newVendor.setUsername(vendorDTO.getUsername());
                newVendor.setPassword(vendorDTO.getPassword());
                vendorRepo.save(newVendor);
                log.info("Vendor " + newVendor.getUsername() + " added to database.");
                response.setMessage("Vendor registered successfully with id : " + newVendor.getId() + ".");
                response.setSuccess(true);
                return response;
            } else {
                response.setMessage("Vendor already exists");
                return response;}
        } catch (Exception e){
            log.error("Vendor registering unsuccessful : " + e.getMessage());
            response.setMessage("Vendor registration unsuccessful");
            return response;
        }
    }

    public VendorResponseDTO login(VendorLoginDTO vendorLoginDTO){ //Read
        VendorResponseDTO response = new VendorResponseDTO();
        try {
            Vendor vendor = vendorRepo.findVendorByUsername(vendorLoginDTO.getUsername());
            if (vendor != null) {
                if (vendor.getPassword().equals(vendorLoginDTO.getPassword())) {
                    response.setMessage("Login successful");
                    response.setSuccess(true);
                    log.info("Vendor " + vendor.getUsername() + " logged in successfully");
                    return response;
                } else {
                    response.setMessage("Password incorrect. Login not successful");
                    log.info("Vendor " + vendor.getUsername() + " login failed due to incorrect password.");
                    return response;
                }
            } else {
                response.setMessage("User doesn't exist. Please register.");
                return response;
            }
        } catch (Exception e){
            response.setMessage("Error occurred while login");
            log.error("Login failed due to an error occurred: " + e.getMessage());
            return response;
        }
    }

    public VendorResponseDTO changePassword(VendorChangePwDTO vendorChangePwDTO){ //Update
        VendorResponseDTO response = new VendorResponseDTO();
        try {
            if (!vendorChangePwDTO.getCurrentPassword().equals(vendorChangePwDTO.getNewPassword())) {
                Vendor vendor = vendorRepo.findVendorByUsername(vendorChangePwDTO.getUsername());
                if (vendor != null) {
                    if (vendor.getPassword().equals(vendorChangePwDTO.getCurrentPassword())) {
                        vendor.setPassword(vendorChangePwDTO.getNewPassword());
                        vendorRepo.save(vendor);
                        response.setMessage("Password changed successfully.");
                        response.setSuccess(true);
                        log.info("Vendor " + vendor.getUsername() + " has changed password successfully.");
                        return response;
                    } else {
                        response.setMessage("Entered password doesnt match the current password.");
                        log.info("Vendor " + vendor.getUsername() + "'s entered password doesnt match the current password.");
                        return response;
                    }

                }
                response.setMessage("User cannot be found.");
                return response;

            } else {
                response.setMessage("Current password and the new password cannot be the same.");
                log.info("Entered new password and old password are the same.");
                return response;
            }
        } catch (Exception e){
            log.error("An exception has occurred while trying to change password: " + e.getMessage());
            response.setMessage("An error occurred while trying to change password.");
            return response;
        }
    }

    public VendorResponseDTO deleteVendorAcc(VendorDeleteDTO vendorDeleteDTO){
        VendorResponseDTO response = new VendorResponseDTO();
        try {
            Vendor vendor = vendorRepo.findVendorByUsername(vendorDeleteDTO.getUsername());
            if (vendor == null) {
                response.setMessage("User not found.");
                log.info("Entered username is not valid.");
                return response;
            } else {
                if (vendor.getPassword().equals(vendorDeleteDTO.getPassword())) {
                    if (vendorDeleteDTO.getConfirmation().equals("DELETE")) {
                        vendorRepo.delete(vendor);
                        response.setMessage("User successfully deleted.");
                        response.setSuccess(true);
                        log.info("Vendor " + vendorDeleteDTO.getUsername() + " deleted account successfully.");
                        return response;
                    } else {
                        response.setMessage("Confirmation is incorrect, properly type 'DELETE' to delete your account.");
                        log.info("Vendor " + vendorDeleteDTO.getUsername() + "'s account delete confirmation failed.");
                        return response;
                    }
                } else {
                    response.setMessage("Password incorrect.");
                    log.info("Password entered by " + vendor.getUsername() + " is incorrect.");
                    return response;
                }
            }
        } catch (Exception e){
            response.setMessage("An error occurred while deleting your account");
            log.error("An exception occurred while deleting Vendor " + vendorDeleteDTO.getUsername() + "'s account: " + e.getMessage());
            return response;
        }
    }

    public String issueTicket(VendorTicketIssueDTO vendorTicketIssueDTO){
        synchronized (this) {
            try {
                Vendor vendor = vendorRepo.findVendorByUsername(vendorTicketIssueDTO.getUsername());
                if (vendor != null) {
                    String response = ticketPool.addTickets(vendorTicketIssueDTO.getNumberOfTickets(), vendorTicketIssueDTO.getEventId());
                    log.info("Vendor " + vendor.getUsername() + ": " + response);
                    return response;
                } else {
                    return "Vendor not found.";
                }
            } catch (Exception e) {
                log.error("An exception occurred while adding tickets by Vendor " + vendorTicketIssueDTO.getUsername() + ": " + e.getMessage());
                return "An error occurred while adding tickets.";
            }
        }
    }
}
