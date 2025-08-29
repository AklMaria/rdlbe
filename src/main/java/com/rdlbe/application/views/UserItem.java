package com.rdlbe.application.views;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.rdlbe.application.business.internal.domains.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserItem {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;  // Cambiato da LocalDateTime a LocalDate
    private User.Role role;
    private Boolean state;
    private Integer credits;
    // private Set<Inscription> inscriptions;
}