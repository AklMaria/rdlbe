package com.rdlbe.application.views;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
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
    private Boolean isActive;
    private LocalDateTime dateStartTime;
    private LocalDateTime dateEndTime;


}
