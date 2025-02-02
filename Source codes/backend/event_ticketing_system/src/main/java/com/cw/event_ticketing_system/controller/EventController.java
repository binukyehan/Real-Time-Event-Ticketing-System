package com.cw.event_ticketing_system.controller;

import com.cw.event_ticketing_system.dto.EventConfigDTO;
import com.cw.event_ticketing_system.dto.EventControlDTO;
import com.cw.event_ticketing_system.dto.EventDeleteDTO;
import com.cw.event_ticketing_system.dto.EventViewDTO;
import java.util.List;
import com.cw.event_ticketing_system.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173") //Allowing to connect with the frontend.
@RequestMapping("/api/event")
public class EventController {
    @Autowired
    private EventService eventService;

    @PostMapping("/create")
    public String createEvent(@RequestBody EventConfigDTO eventConfigDTO){
        return eventService.createEvent(eventConfigDTO);
    }

    @DeleteMapping("/delete")
    public String deleteEvent(@RequestBody EventDeleteDTO eventDeleteDTO){
        return eventService.deleteEvent(eventDeleteDTO);
    }

    @GetMapping("/all")
    public List<EventViewDTO> getAllEvents() {
        return eventService.getAllEvents();
    }

    @PutMapping("/control-panel")
    public String controlEvent(@RequestBody EventControlDTO eventControlDTO){
        return eventService.controlEventStatus(eventControlDTO);
    }

}
