package com.rdlbe.application.business.internal.domains;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @Column(name = "link")
    private String link;


    @Column(name = "date")
    private LocalDate date;

    @Column(name ="time")
    private LocalTime time;

    @Column (name = "duration")
    private Integer duration;

//    @Column(name = "date_end_time")
//    private LocalDateTime dateEndTime;

}
