package com.alumnimanagement.web.controller;

import com.alumnimanagement.services.def.EventRegistrationService;
import com.alumnimanagement.web.dto.EventRegistrationDTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/event-registrations")
public class EventRegistrationController {

    private final EventRegistrationService eventRegistrationService;

    @Autowired
    public EventRegistrationController(EventRegistrationService eventRegistrationService) {
        this.eventRegistrationService = eventRegistrationService;
    }

    @PostMapping("/register")
    public void registerForEvent(@RequestBody EventRegistrationDTO eventRegistrationDTO) {
        eventRegistrationService.registerForEvent(eventRegistrationDTO);
    }

    @GetMapping
    public List<EventRegistrationDTO> getEventRegistrations() {
        return eventRegistrationService.getEventRegistrations();
    }

    @GetMapping("/search")
    public List<EventRegistrationDTO> searchEventRegistrations(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String speaker) {
        return eventRegistrationService.searchEventRegistrations(name, speaker);
    }
}