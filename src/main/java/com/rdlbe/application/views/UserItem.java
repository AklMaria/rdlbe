package com.rdlbe.application.views;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.rdlbe.application.business.internal.domains.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserItem {

    private Long id;
    private String username;
    private String email;
    private LocalDateTime birthDate;
    private User.Role role;
    // private Set<Inscription> inscriptions;
}
