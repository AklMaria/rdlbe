package com.rdlbe.application.business.publishing;

import com.rdlbe.application.views.ClassroomItem;
import com.rdlbe.application.views.ClassroomRequest;
import com.rdlbe.application.views.ClassroomUsersDetailsItem;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ClassroomService {

    List<ClassroomItem> getAllClassrooms();
    Optional<ClassroomItem> getClassroomById(Long id);
    ClassroomItem createClassroom(ClassroomItem classroom);
    ClassroomItem updateClassroom(Long id, ClassroomRequest classroom);
    void deleteClassroom(Long id);
    ClassroomUsersDetailsItem getClassroomUsersById(Long classroomId);


}
