package com.rdlbe.application.views;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClassroomRequest {

    private Optional<String> name;
    private Optional<String> description;
    private Optional<Integer> maxSeats;
    private Optional<Boolean> isActive;
    private Optional<LocalDateTime> dateStartTime;
    private Optional<LocalDateTime> dateEndTime;
}
