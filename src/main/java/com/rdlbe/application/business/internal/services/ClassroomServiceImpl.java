package com.rdlbe.application.business.internal.services;

import com.rdlbe.application.business.internal.dao.presentation.ClassroomDAO;
import com.rdlbe.application.business.publishing.ClassroomService;
import com.rdlbe.application.views.ClassroomInsertItem;
import com.rdlbe.application.views.ClassroomItem;
import com.rdlbe.application.views.ClassroomUpdateItem;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomDAO classroomDAO;

    private ModelMapper modelMapper;

    public ClassroomServiceImpl(ClassroomDAO classroomDAO) {
        this.classroomDAO = classroomDAO;
    }


    @Override
    public List<ClassroomItem> getAllClassrooms() {
        return List.of();
    }

    @Override
    public Optional<ClassroomItem> getClassroomById(Long id) {
        return Optional.empty();
    }

    @Override
    public ClassroomItem createClassroom(ClassroomInsertItem classroom) {
        return null;
    }

    @Override
    public ClassroomItem updateClassroom(Long id, ClassroomUpdateItem classroom) {
        return null;
    }

    @Override
    public void deleteClassroom(Long id) {

    }
}
