package com.cw.event_ticketing_system.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerTicketPurchaseDTO {
    private String name;
    private Long eventId;
    private int numberOfTickets;
}
