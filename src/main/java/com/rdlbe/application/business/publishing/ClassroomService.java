package com.rdlbe.application.business.publishing;

import com.rdlbe.application.views.ClassroomItem;
import com.rdlbe.application.views.ClassroomRequest;
import com.rdlbe.application.views.ClassroomUsersDetailsItem;
import com.rdlbe.application.views.DocumentItem;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ClassroomService {

    List<ClassroomItem> getAllClassrooms();
    List<ClassroomItem> getCompletedClassrooms(Long userId);
    Optional<ClassroomItem> getClassroomById(Long id);
    ClassroomItem createClassroom(ClassroomItem classroom);
    ClassroomItem updateClassroom(Long id, ClassroomRequest classroom);
    void deleteClassroom(Long id);
    ClassroomUsersDetailsItem getClassroomUsersById(Long classroomId);
    List<ClassroomItem> getClassroomsByUser(Long userId);
    List<DocumentItem> getClassroomDocs(Long classroomId);
    void uploadDoc(Long classroomId, MultipartFile file);
    void deleteDoc(Long classroomId, Long docId);
    DocumentItem findById(Long id);
    org.springframework.core.io.Resource getDocumentAsResource(DocumentItem document);


}
