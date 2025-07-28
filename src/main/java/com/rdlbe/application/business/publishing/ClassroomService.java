package com.rdlbe.application.business.publishing;

import com.rdlbe.application.views.ClassroomItem;
import com.rdlbe.application.views.ClassroomRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ClassroomService {

    List<ClassroomItem> getAllClassrooms();
    Optional<ClassroomItem> getClassroomById(Long id);
    ClassroomItem createClassroom(ClassroomItem classroom);
    ClassroomItem updateClassroom(Long id, ClassroomRequest classroom);
    void deleteClassroom(Long id);

    //Query personalizzate
    List<ClassroomItem> getClassroomsByUserAndDate(Long userId, LocalDateTime date);
    List<ClassroomItem> getAvailableClassroomsByDate(LocalDateTime date);
    List<ClassroomItem> getClassroomsByUserInDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate);


}
