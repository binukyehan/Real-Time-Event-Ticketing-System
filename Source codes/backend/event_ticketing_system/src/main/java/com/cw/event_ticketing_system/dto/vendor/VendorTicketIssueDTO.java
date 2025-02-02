package com.cw.event_ticketing_system.dto.vendor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorTicketIssueDTO {
    private String username;
    private Long eventId;
    private int numberOfTickets;
}
