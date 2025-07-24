package com.rdlbe.application.business.internal.domains;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "inscriptions")
@Getter
@Setter
@ToString
public class Inscription {

    @EmbeddedId
    private InscriptionId id;

    @ManyToOne(fetch = FetchType.LAZY) // Aggiunto LAZY per performance
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY) // Aggiunto LAZY per performance
    @MapsId("classroomId")
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    // Usato boolean primitivo per evitare valori null
    private boolean registration = false;
}

