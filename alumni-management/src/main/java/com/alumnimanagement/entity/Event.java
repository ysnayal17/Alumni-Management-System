package com.alumnimanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "Event")
public class Event {

    @Id
    private String id;
    @Column(nullable = false)
    private LocalDateTime datetime;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String speaker;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String registrationLink;
}
