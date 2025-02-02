package com.cw.event_ticketing_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EventViewDTO {
    private Long eventId;
    private String eventName;
    private int currentNoOfTickets;
    private boolean eventStatus;
}