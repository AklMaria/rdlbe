package com.rdlbe.application.views;

import lombok.Data;
import java.util.List;

@Data
public class ClassroomUsersDetailsItem {
    private Long classroomId;
    private String name;
    private String description;
    private int totalUsers;
    private List<UserSummary> users;
}
