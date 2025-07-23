package com.rdlbe.application.business.internal.services;

import com.rdlbe.application.business.internal.dao.presentation.ClassroomDAO;
import com.rdlbe.application.business.internal.domains.Classroom;
import com.rdlbe.application.business.publishing.ClassroomService;
import com.rdlbe.application.views.ClassroomInsertItem;
import com.rdlbe.application.views.ClassroomItem;
import com.rdlbe.application.views.ClassroomUpdateItem;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomDAO classroomDAO;
    private final ModelMapper modelMapper;

    public ClassroomServiceImpl(ClassroomDAO classroomDAO, ModelMapper modelMapper) {
        this.classroomDAO = classroomDAO;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    private void init() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.typeMap(Classroom.class, ClassroomItem.class);
    }


    @Override
    public List<ClassroomItem> getAllClassrooms() {
        // Essendo una getAllClassrooms non servono filtri di ricerca.
        // Se dovessero servirti filtri, devi mandarli dal Controller
        var items = classroomDAO.find(null)
                .stream()
                .map(b -> modelMapper.map(b, ClassroomItem.class))
                .toList();
        return items;
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
