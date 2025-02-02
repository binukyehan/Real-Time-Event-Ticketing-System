package com.cw.event_ticketing_system.dto.vendor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorChangePwDTO {
    private String username;
    private String currentPassword;
    private String newPassword;
}
