package com.rdlbe.application.business.internal.domains;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;


@Getter
@Setter
@ToString
@Entity
@Table(name = "classrooms")
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Integer maxSeats;
    private Boolean isActive;

    @Column(name = "date_start_time")
    private LocalDateTime dateStartTime;

    @Column(name = "date_end_time")
    private LocalDateTime dateEndTime;

   // @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL, orphanRemoval = true)
  //  @ToString.Exclude // Evita cicli infiniti nel toString
   // private Set<Inscription> inscriptions;
}
