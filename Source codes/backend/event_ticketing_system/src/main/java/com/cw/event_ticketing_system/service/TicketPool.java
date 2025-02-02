package com.cw.event_ticketing_system.service;

import com.cw.event_ticketing_system.entity.Event;
import com.cw.event_ticketing_system.repository.EventRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
public class TicketPool {
    //private static final List<Integer> tickets = Collections.synchronizedList(new LinkedList<>());
    private final ExecutorService executorService;
    private final EventRepo eventRepo;
    private final ReentrantLock lockToAdd = new ReentrantLock();
    private final ReentrantLock lockToRemove = new ReentrantLock();

    private int addedCount;

    public TicketPool(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
        this.executorService = Executors.newCachedThreadPool();
    }

    public String addTickets(int ticketCount, Long eventId) {
        lockToAdd.lock(); //Acquiring lock
        try {
            if (ticketCount<=0){
                return "Number of tickets should be a positive number.";
            }
            Event event = eventRepo.findById(eventId).orElseThrow(() -> new EntityNotFoundException("There is no event under the given event id: " + eventId));
            if (event != null) {
                if (event.getTotalTicketsAdded() < event.getMaxTicketCapacity()) { //Checking if the tickets for the event are full.
                    if (event.getCurrentNoOfTickets() < event.getTotalTickets()) { //Checking if the Ticket pool is full
                        if(event.isEventStatus()) {
                            Future<Integer> future = executorService.submit(() -> {
                                addedCount = 0;
                                for (int i = 0; i < ticketCount; i++) {
                                    Event event1 = eventRepo.findById(eventId).orElseThrow(() -> new EntityNotFoundException("There is no event under the given event id: " + eventId));
                                    if (event1.getCurrentNoOfTickets() < event1.getTotalTickets()) { //Adding tickets till the ticket pool capacity is reached.
                                        event1.setCurrentNoOfTickets(event1.getCurrentNoOfTickets() + 1);
                                        event1.setTotalTicketsAdded(event1.getTotalTicketsAdded() + 1);
                                        eventRepo.save(event1);
                                        log.info("1 ticket added. Total tickets in the pool: " + event1.getCurrentNoOfTickets());
                                        addedCount++;
                                    } else {
                                        log.info("Tickets capacity reached.");
                                        break;
                                    }
                                    try {
                                        Thread.sleep(1000 / event1.getTicketReleaseRate()); //todo calculate the formula
                                    } catch (InterruptedException e) {
                                        Thread.currentThread().interrupt();
                                        log.error("Thread was interrupted");
                                    }
                                }
                                return ticketCount - addedCount;
                            });
                            try {
                                int ticketsAddedThisRound = future.get();
                                if (ticketsAddedThisRound == 0) {
                                    return "All " + addedCount + " tickets added successfully"; //TODO fix showing the total tickets added by several vendors together when multiple requests were given.
                                } else {
                                    return "Only " + addedCount + " tickets were added out of " + ticketCount + " tickets.";
                                }
                            } catch (InterruptedException | ExecutionException e) {
                                Thread.currentThread().interrupt();
                                log.error("An exception occurred while processing the added tickets.");
                                return "An error occurred while processing the added number of tickets";
                            }
                        } else {
                            return "This event has not started yet.";
                        }
                    } else {
                        log.info("Ticket pool is currently full.");
                        return "Ticket pool is currently full. Wait till a customer buys tickets.";
                    }
                } else {
                    log.info("Maximum ticket capacity for event with id " + event.getEventId() + "is reached");
                    return "Maximum ticket capacity is reached for this event.";
                }
            } else {
                return "There is no event under the given event id.";
            }
        }catch (EntityNotFoundException e){
            log.error(e.getMessage());
            return "There is no event under the given event id: " + eventId;
        } catch (Exception e){
            log.error("An exception occurred while adding tickets to the pool: " + e.getMessage());
            return "An error occurred while adding tickets to the ticket pool.";
        } finally {
            lockToAdd.unlock(); //Releasing the lock
        }
    }

    public String removeTickets(int ticketCount, Long eventId){
        lockToRemove.lock(); //Acquiring the lock
        try {
            if (ticketCount<=0){
                return "Number of tickets should be a positive number.";
            }
            Event event = eventRepo.findById(eventId).orElseThrow(() -> new EntityNotFoundException("There is no event under the given event id: " + eventId));
            if (event != null) {
                if (event.getTotalTicketsBought() < event.getMaxTicketCapacity()) {
                    if (event.getCurrentNoOfTickets() - ticketCount >= 0) {
                        if(event.isEventStatus()) {
                            Future<String> future = executorService.submit(() -> {
                                for (int i = 0; i < ticketCount; i++) {
                                    Event event1 = eventRepo.findById(eventId).orElseThrow(() -> new EntityNotFoundException("There is no event under the given event id: " + eventId)); //todo recheck this.
                                    if (event1.getCurrentNoOfTickets() > 0) { //Checking if tickets are available
                                        event1.setCurrentNoOfTickets(event1.getCurrentNoOfTickets() - 1);
                                        event1.setTotalTicketsBought(event1.getTotalTicketsBought() + 1);
                                        eventRepo.save(event1);
                                        log.info("1 ticket bought. Total tickets in the pool: " + event1.getCurrentNoOfTickets());
                                    } else {
                                        log.info("Ticket pool is empty.");
                                        break;
                                    }
                                    try {
                                        Thread.sleep(1000 / event1.getCustomerRetrievalRate()); //todo calculate the formula
                                    } catch (InterruptedException e) {
                                        Thread.currentThread().interrupt();
                                        log.error("Thread was interrupted");
                                    }
                                }
                                return ticketCount + " Tickets bought successfully.";
                            });
                            try {
                                String result = future.get();
                                return result;
                            } catch (InterruptedException | ExecutionException e) {
                                Thread.currentThread().interrupt();
                                log.error("An exception occurred while processing the bought tickets.");
                                return "An error occurred while processing the bought number of tickets";
                            }
                        } else {
                            return "This event has not started yet.";
                        }
                    } else {
                        if (event.getCurrentNoOfTickets() == 0){
                            return "Ticket pool is currently empty. Wait till vendors release tickets again.";
                        } else {
                            log.info("Only " + event.getCurrentNoOfTickets() + " tickets available for purchase");
                            return "Only " + event.getCurrentNoOfTickets() + " tickets available for purchase";
                        }
                    }
                } else {
                    log.info(event.getEventName() + " event is sold out");
                    return "The event is sold out.";
                }
            } else {
                return "There is no event under the given event id.";
            }
        }catch (EntityNotFoundException e){
            log.error(e.getMessage());
            return "There is no event under the given event id: " + eventId;
        } catch (Exception e){
            log.error("An exception occurred while buying tickets from the pool: " + e.getMessage());
            return "An error occurred while buying tickets.";
        } finally {
            lockToRemove.unlock(); //Releasing the lock
        }
    }
}
