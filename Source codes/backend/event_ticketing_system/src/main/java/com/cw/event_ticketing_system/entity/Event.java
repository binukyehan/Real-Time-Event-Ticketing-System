package com.cw.event_ticketing_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long eventId;
    private String eventName;
    private String eventLocation;
    private String eventDate;
    private int maxTicketCapacity; //Total ticket capacity for the event
    private int totalTickets; //cap for ticket pool at an instance
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int totalTicketsAdded; //Total no. of tickets added which will stop when the ticket capacity is reached.
    private int currentNoOfTickets; //Currently available number of tickets in the ticket pool.
    private int totalTicketsBought; //Total no. of tickets bought by the customers. this will stop when it reaches the ticket capacity.
    private boolean eventStatus = false;

}
