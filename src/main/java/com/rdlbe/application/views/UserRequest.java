package com.rdlbe.application.views;

import java.time.LocalDateTime;
import java.util.Optional;

public class UserRequest {
    private Optional<String> username;
    private Optional<String> email;
    private Optional<LocalDateTime> birthDate;
    private Optional<String> role;
}
