package com.rdlbe.application.business.internal.domains;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;


    private byte[] password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;


    @Enumerated(EnumType.STRING)
    @Column(name = "user_level", nullable = false)
    private UserLevel userLevel = UserLevel.BEGINNER; // valore di default

    @Column(nullable = false)
    private Boolean state = false;  // default true

    @Column(nullable = false)
    private Integer credits = 0;   // default 0

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<Inscription> inscriptions;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Document> documents;

    public enum Role {
        ADMIN,
        USER
    }

    public enum UserLevel {
        BEGINNER,
        INTERMEDIATE,
        ADVANCED
    }
}