package com.cw.event_ticketing_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventConfigDTO {
    private String eventName;
    private String eventLocation;
    private String eventDate;
    private int maxTicketCapacity; //Total ticket capacity for the event
    private int totalTickets; //cap for ticket pool at an instance
    private int ticketReleaseRate;
    private int CustomerRetrievalRate;
}
