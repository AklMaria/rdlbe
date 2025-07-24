package com.rdlbe.application.business.publishing;

import com.rdlbe.application.views.ClassroomInsertItem;
import com.rdlbe.application.views.ClassroomItem;
import com.rdlbe.application.views.ClassroomUpdateItem;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ClassroomService {

    List<ClassroomItem> getAllClassrooms();
    Optional<ClassroomItem> getClassroomById(Long id);
    ClassroomItem createClassroom(ClassroomInsertItem classroom);
    ClassroomItem updateClassroom(Long id, ClassroomUpdateItem classroom);
    void deleteClassroom(Long id);

    //Query personalizzate
    List<ClassroomItem> getClassroomsByUserAndDate(Long userId, LocalDateTime date);
    List<ClassroomItem> getAvailableClassroomsByDate(LocalDateTime date);
    List<ClassroomItem> getClassroomsByUserInDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate);


}
