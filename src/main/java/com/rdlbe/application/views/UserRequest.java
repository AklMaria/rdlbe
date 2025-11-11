package com.rdlbe.application.views;

import com.rdlbe.application.business.internal.domains.User;
import lombok.Data;


import java.time.LocalDate;



@Data
public class UserRequest {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;
    private User.Role role;  // Deve essere "USER" o "ADMIN"
    private User.UserLevel userLevel;
    private Boolean state;
    private Integer credits;
    private String password;

}