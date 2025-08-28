package com.rdlbe.application.views;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClassroomItem {
    private Long id;
    private String name;
    private String description;
    private Integer maxSeats;
    private Integer availableSeats;
    private String link;
    private Boolean isActive;
    private LocalDate date;
   // private LocalDateTime dateEndTime;
    private LocalTime time;
    private Integer duration;
}
