package com.alumnimanagement.repo;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import com.alumnimanagement.entity.Event;

public interface EventRegistrationRepository extends JpaRepository<Event, String> {

    @Query("SELECT e FROM Event e WHERE " +
           "(:eventName IS NULL OR e.name LIKE %:eventName%) AND " +
           "(:speaker IS NULL OR e.speaker LIKE %:speaker%)")
    List<Event> findByEventNameOrSpeaker(@Param("eventName") String name,
                                         @Param("speaker") String speaker);
    
    // @Query("SELECT e FROM Event e WHERE (:name IS NULL OR e.name LIKE %:name%)")
    // List<Event> findByEventName(@Param("name") String name);

}