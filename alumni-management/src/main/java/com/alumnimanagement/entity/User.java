package com.alumnimanagement.entity;

import com.alumnimanagement.enums.Department;
import com.alumnimanagement.enums.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Users")
public class User {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;
    private String password;

    @Column(nullable = false)
    private LocalDate dateOfGraduation;
    @Column(nullable = false)
    private Department department;

    @Column(length = 10, nullable = false)
    private String mobileNo;

    private String linkedinProfile;
    @Column(nullable = false)
    private Role role;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    private Company company;

    @Lob
    private byte[] profileImage;
    private String skills;

    @Column(nullable = false, unique = true)
    private String email;
}
