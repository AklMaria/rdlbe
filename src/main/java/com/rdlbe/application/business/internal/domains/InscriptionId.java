package com.rdlbe.application.business.internal.domains;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class InscriptionId implements Serializable {

    private static final long serialVersionUID = 1L; // Buona pratica per Serializable

    private Long userId;

    // --- CORREZIONE 3: Rinominato il campo per coerenza ---
    // Il campo qui deve chiamarsi "classroomId" per corrispondere
    // all'attributo @MapsId("classroomId") nella classe Inscription.
    // Prima si chiamava "roomId", causando un errore.
    private Long classroomId;

    public InscriptionId() {}

    public InscriptionId(Long userId, Long classroomId) {
        this.userId = userId;
        this.classroomId = classroomId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InscriptionId that = (InscriptionId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(classroomId, that.classroomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, classroomId);
    }
}
