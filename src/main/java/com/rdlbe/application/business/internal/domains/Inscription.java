package com.rdlbe.application.business.internal.domains;

import jakarta.persistence.*;



@Entity
@Table(name = "inscriptions")
public class Inscription {

    @EmbeddedId
    private InscriptionId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("classroomId")
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    private Boolean registration = false;


}

