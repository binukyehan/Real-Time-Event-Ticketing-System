package com.cw.event_ticketing_system.service;

import java.util.stream.Collectors;
import com.cw.event_ticketing_system.dto.EventConfigDTO;
import com.cw.event_ticketing_system.dto.EventControlDTO;
import com.cw.event_ticketing_system.dto.EventDeleteDTO;
import com.cw.event_ticketing_system.dto.EventViewDTO;
import com.cw.event_ticketing_system.entity.Event;
import com.cw.event_ticketing_system.repository.EventRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@Slf4j
public class EventService {
    @Autowired
    private EventRepo eventRepo;

    public String createEvent(EventConfigDTO eventConfigDTO){
        try {
            if (eventConfigDTO.getTotalTickets()>eventConfigDTO.getMaxTicketCapacity()){
                throw new Exception("Max ticket Capacity cannot be lower than the total tickets. ");
            } else if (eventConfigDTO.getMaxTicketCapacity() <= 0 || eventConfigDTO.getTotalTickets() <=0
            || eventConfigDTO.getTicketReleaseRate()<=0 || eventConfigDTO.getCustomerRetrievalRate()<=0) {
                throw new Exception("Entered values should be positive.");
            }
            Event newTicketPool = new Event();
            newTicketPool.setEventName(eventConfigDTO.getEventName());
            newTicketPool.setEventLocation(eventConfigDTO.getEventLocation());
            newTicketPool.setEventDate(eventConfigDTO.getEventDate());
            newTicketPool.setMaxTicketCapacity(eventConfigDTO.getMaxTicketCapacity());
            newTicketPool.setTotalTickets(eventConfigDTO.getTotalTickets());
            newTicketPool.setTicketReleaseRate(eventConfigDTO.getTicketReleaseRate());
            newTicketPool.setCustomerRetrievalRate(eventConfigDTO.getCustomerRetrievalRate());
            newTicketPool.setTotalTicketsAdded(0);
            eventRepo.save(newTicketPool);
            log.info("New event created for " + newTicketPool.getEventName());
            return "New event with " + newTicketPool.getTotalTickets() + " ticket pool capacity is created.";
        } catch (Exception e){
            log.error("An exception occurred while creating the new event." + e.getMessage());
            return e.getMessage();
        }
    }

    public String deleteEvent(EventDeleteDTO eventDeleteDTO){
        try {
            Event event = eventRepo.findById(eventDeleteDTO.getId()).orElseThrow(() -> new EntityNotFoundException("There is no event under the entered event id: " + eventDeleteDTO.getId()));
            if (eventDeleteDTO.getConfirmation().equals("DELETE")) {
                log.info("Event " + event.getEventName() + " deleted successfully.");
                eventRepo.delete(event);
                return "Event deleted successfully";
            } else {
                return "Confirmation is incorrect, properly type 'DELETE' to delete the event.";
            }
        } catch (EntityNotFoundException e){
            log.error(e.getMessage());
            return "There is no event under the entered event id: " + eventDeleteDTO.getId();
        } catch (Exception e){
            log.error("An exception occurred while deleting an event with id " + eventDeleteDTO.getId());
            return "An error occurred while deleting the event.";
        }
    }
    public List<EventViewDTO> getAllEvents() {
        try {
            List<Event> events = eventRepo.findAll(); // Fetch all events from the database
//            log.info("Retrieved " + events.size() + " events from the database.");

            // Convert Event entities to EventViewDTOs
            return events.stream().map(event -> new EventViewDTO(
                    event.getEventId(),
                    event.getEventName(),
                    event.getCurrentNoOfTickets(),
                    event.isEventStatus()
            )).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("An exception occurred while retrieving all events.");
            throw new RuntimeException("An error occurred while fetching the events.");
        }
    }

    public String controlEventStatus(EventControlDTO eventControlDTO){
        try {
            Event event = eventRepo.findById(eventControlDTO.getId()).orElseThrow(() -> new EntityNotFoundException("There is no event under the entered event id: " + eventControlDTO.getId()));
            boolean enteredStatus = eventControlDTO.isEventStatus();
            boolean currentStatus = event.isEventStatus();

            if (enteredStatus == currentStatus){
                return "Event is already " + (currentStatus? "started." : "stopped.");
            }

            event.setEventStatus(enteredStatus);
            eventRepo.save(event);
            log.info("Event "+ eventControlDTO.getId() + " is "+ (enteredStatus? "started": "stopped") + " successfully.");
            return "Event "+ eventControlDTO.getId() + " is "+ (enteredStatus? "started": "stopped") + " successfully.";
        } catch (Exception e){
            log.error("An error occurred while updating the event status." + e);
            return "An error occurred while updating the event status.";
        }
    }


}
