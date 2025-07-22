package com.rdlbe.application.business.publishing;

import com.rdlbe.application.views.ClassroomItem;

import java.util.List;

public interface ClassroomService {

    List<ClassroomItem> findClassrooms();
    ClassroomItem findClassroomById(long id);
}
