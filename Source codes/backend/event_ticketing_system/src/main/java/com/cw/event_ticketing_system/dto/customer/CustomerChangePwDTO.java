package com.cw.event_ticketing_system.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerChangePwDTO {
    private String username;
    private String currentPassword;
    private String newPassword;
}
