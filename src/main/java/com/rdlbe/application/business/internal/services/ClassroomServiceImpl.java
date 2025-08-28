package com.rdlbe.application.business.internal.services;

import com.rdlbe.application.business.internal.dao.presentation.ClassroomDAO;
import com.rdlbe.application.business.internal.dao.presentation.InscriptionDAO;
import com.rdlbe.application.business.internal.domains.Classroom;
import com.rdlbe.application.business.internal.domains.User;
import com.rdlbe.application.business.publishing.ClassroomService;
import com.rdlbe.application.views.ClassroomItem;
import com.rdlbe.application.views.ClassroomRequest;
import com.rdlbe.application.views.ClassroomUsersDetailsItem;
import com.rdlbe.application.views.UserSummary;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomDAO classroomDAO;
    private final ModelMapper modelMapper;
    private final InscriptionDAO inscriptionDAO;

    public ClassroomServiceImpl(ClassroomDAO classroomDAO, InscriptionDAO inscriptionDAO, ModelMapper modelMapper) {
        this.classroomDAO = classroomDAO;
        this.inscriptionDAO = inscriptionDAO;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    private void init() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.typeMap(Classroom.class, ClassroomItem.class);
        modelMapper.typeMap(ClassroomItem.class, Classroom.class);
        modelMapper.typeMap(Classroom.class, ClassroomRequest.class);
        modelMapper.typeMap(ClassroomRequest.class, Classroom.class);
    }


    @Override
    public List<ClassroomItem> getAllClassrooms() {
        return classroomDAO.find(null)
                .stream()
                .map(classroom -> {
                    ClassroomItem dto = modelMapper.map(classroom, ClassroomItem.class);
                    int enrolled = inscriptionDAO.countByClassroom(classroom.getId());
                    int max = Optional.ofNullable(classroom.getMaxSeats()).orElse(0);
                    dto.setAvailableSeats(Math.max(0, max - enrolled)); // protezione contro valori negativi
                    return dto;
                })
                .toList();
    }
    @Override
    public Optional<ClassroomItem> getClassroomById(Long id) {
        return classroomDAO.findById(id)
                .map(classroom -> {
                    ClassroomItem dto = modelMapper.map(classroom, ClassroomItem.class);
                    int enrolled = inscriptionDAO.countByClassroom(classroom.getId());
                    int max = Optional.ofNullable(classroom.getMaxSeats()).orElse(0);
                    dto.setAvailableSeats(Math.max(0, max - enrolled));
                    return dto;
                });
    }

    @Override
    public ClassroomItem createClassroom(ClassroomItem classroomDto) {
        // Mappa il DTO in entità
        Classroom classroom = modelMapper.map(classroomDto, Classroom.class);
        log.info("Mapped Classroom before insert: {}", classroom); // <-- logga tutti i campi

        Long id = classroomDAO.create(classroom); // Il DAO deve restituire l'ID creato
        classroom.setId(id);
        return modelMapper.map(classroom, ClassroomItem.class);
    }

    @Override
    public ClassroomItem updateClassroom(Long id, ClassroomRequest classroomDto) {
        Classroom classroom = modelMapper.map(classroomDto, Classroom.class);
        classroom.setId(id);
        classroomDAO.update(classroom);
        return modelMapper.map(classroom, ClassroomItem.class);
    }

    @Override
    public void deleteClassroom(Long id) {
        classroomDAO.delete(id, null); // se non serve idUtenteAggiornamento, passiamo null
    }

//    //Query personalizzate
//    @Override
//    public List<ClassroomItem> getClassroomsByUserAndDate(Long userId, LocalDateTime date) {
//        var classrooms = classroomDAO.findByUserAndDate(userId, date)
//                .stream()
//                .map(c -> modelMapper.map(c, ClassroomItem.class))
//                .toList();
//        return classrooms;
//    }

//    @Override
//    public List<ClassroomItem> getAvailableClassroomsByDate(LocalDateTime date) {
//        var classrooms = classroomDAO.findAvailableByDate(date)
//                .stream()
//                .map(c -> modelMapper.map(c, ClassroomItem.class))
//                .toList();
//        return classrooms;
//    }

//    @Override
//    public List<ClassroomItem> getClassroomsByUserInDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
//        var classrooms = classroomDAO.findByUserInDateRange(userId, startDate, endDate)
//                .stream()
//                .map(c -> modelMapper.map(c, ClassroomItem.class))
//                .toList();
//        return classrooms;
//    }

    @Override
    public ClassroomUsersDetailsItem getClassroomUsersById(Long classroomId) {
        Classroom classroom = classroomDAO.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));

        // Utenti iscritti alla classroom
        List<User> users = inscriptionDAO.findUsersByClassroom(classroomId);

        ClassroomUsersDetailsItem dto = new ClassroomUsersDetailsItem();
        dto.setClassroomId(classroom.getId());
        dto.setName(classroom.getName());
        dto.setDescription(classroom.getDescription());
        dto.setTotalUsers(users.size());

        List<UserSummary> summaries = users.stream().map(u -> {
            UserSummary summary = new UserSummary();
            summary.setId(u.getId());
            summary.setUsername(u.getUsername());
            summary.setEmail(u.getEmail());
            return summary;
        }).toList();

        dto.setUsers(summaries);
        return dto;
    }


}
