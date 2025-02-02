package com.cw.event_ticketing_system.controller;

import com.cw.event_ticketing_system.dto.EventViewDTO;
import com.cw.event_ticketing_system.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@Controller
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/event")
public class EventSSEController {
    @Autowired
    private EventService eventService;

    @GetMapping("/stream")
    public SseEmitter streamEvents(){
        SseEmitter emitter = new SseEmitter();
        Thread thread = new Thread(() -> {
            try{
                while (true){
                    List<EventViewDTO> events = eventService.getAllEvents();
                    emitter.send(events, MediaType.APPLICATION_JSON);
                }
            } catch (Exception e){
                emitter.completeWithError(e);
            } finally {
                emitter.complete();
            }
        });
        thread.setDaemon(true);
        thread.start();
        return emitter;
    }

}
