package com.rdlbe.application.views;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String mail;
    private String password;
}

