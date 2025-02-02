package com.cw.event_ticketing_system.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDeleteDTO {
    private String username;
    private String password;
    private String confirmation;
}
