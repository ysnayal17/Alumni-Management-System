package com.alumnimanagement.services.def;

import java.util.List;

import com.alumnimanagement.web.dto.EventRegistrationDTO;

public interface EventRegistrationService {
    void registerForEvent(EventRegistrationDTO eventRegistrationDTO);
    List<EventRegistrationDTO> getEventRegistrations();
    List<EventRegistrationDTO> searchEventRegistrations(String name, String speaker);
}
