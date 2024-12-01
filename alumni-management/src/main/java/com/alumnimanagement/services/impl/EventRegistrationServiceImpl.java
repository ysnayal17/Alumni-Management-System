package com.alumnimanagement.services.impl;

import com.alumnimanagement.entity.Event;
import com.alumnimanagement.repo.EventRegistrationRepository;
import com.alumnimanagement.services.def.EventRegistrationService;
import com.alumnimanagement.web.dto.EventRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EventRegistrationServiceImpl implements EventRegistrationService {

    private final EventRegistrationRepository eventRegistrationRepository;

    @Autowired
    public EventRegistrationServiceImpl(EventRegistrationRepository eventRegistrationRepository) {
        this.eventRegistrationRepository = eventRegistrationRepository;
    }

    @Override
    public void registerForEvent(EventRegistrationDTO eventRegistrationDTO) {
        Event event = new Event();
        event.setId(UUID.randomUUID().toString());
        event.setDatetime(eventRegistrationDTO.getEventDate());
        event.setName(eventRegistrationDTO.getEventName());
        event.setSpeaker(eventRegistrationDTO.getSpeaker());
        event.setLocation(eventRegistrationDTO.getLocation());
        event.setDescription(eventRegistrationDTO.getDescription());
        event.setRegistrationLink(eventRegistrationDTO.getRegistrationLink());

        eventRegistrationRepository.save(event);
    }

     @Override
    public List<EventRegistrationDTO> getEventRegistrations() {
        List<Event> eventRegistrations = eventRegistrationRepository.findAll();
        return eventRegistrations.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<EventRegistrationDTO> searchEventRegistrations(String eventName, String speaker) {
        List<Event> events = eventRegistrationRepository.findByEventNameOrSpeaker(eventName, speaker);
        return events.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    
    private EventRegistrationDTO convertToDTO(Event event) {
        return EventRegistrationDTO.builder()
                .eventId(UUID.randomUUID().toString())
                .eventDate(event.getDatetime())
                .eventName(event.getName())
                .speaker(event.getSpeaker())
                .location(event.getLocation())
                .description(event.getDescription())
                .registrationLink(event.getRegistrationLink())
                .build();
    }
}