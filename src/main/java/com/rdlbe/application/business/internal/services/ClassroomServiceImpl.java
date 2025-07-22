package com.rdlbe.application.business.internal.services;

import com.rdlbe.application.business.internal.dao.presentation.ClassroomDAO;
import com.rdlbe.application.business.publishing.ClassroomService;
import com.rdlbe.application.views.ClassroomItem;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomDAO classroomDAO;

    private ModelMapper modelMapper;

    public ClassroomServiceImpl(ClassroomDAO classroomDAO) {
        this.classroomDAO = classroomDAO;
    }

    @Override
    public List<ClassroomItem> findClassrooms() {
        return List.of();
    }

    @Override
    public ClassroomItem findClassroomById(long id) {
        var classroom = classroomDAO.getClassroomById(id);
        //TODO: map object to ClassroomItem
        return modelMapper.map(classroom, ClassroomItem.class);
    }
}
