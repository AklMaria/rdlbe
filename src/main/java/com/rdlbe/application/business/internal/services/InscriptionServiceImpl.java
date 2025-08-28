package com.rdlbe.application.business.internal.services;

import com.rdlbe.application.business.internal.dao.presentation.ClassroomDAO;
import com.rdlbe.application.business.internal.dao.presentation.InscriptionDAO;
import com.rdlbe.application.business.internal.dao.presentation.UserDAO;
import com.rdlbe.application.business.internal.domains.Classroom;
import com.rdlbe.application.business.internal.domains.Inscription;
import com.rdlbe.application.business.internal.domains.InscriptionId;
import com.rdlbe.application.business.internal.domains.User;
import com.rdlbe.application.business.publishing.InscriptionService;
import com.rdlbe.application.views.ClassroomItem;
import com.rdlbe.application.views.InscriptionRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class InscriptionServiceImpl implements InscriptionService {

    private final InscriptionDAO inscriptionDAO;
    private final UserDAO userDAO;
    private final ClassroomDAO classroomDAO;

    public InscriptionServiceImpl(InscriptionDAO inscriptionDAO, UserDAO userDAO, ClassroomDAO classroomDAO) {
        this.inscriptionDAO = inscriptionDAO;
        this.userDAO = userDAO;
        this.classroomDAO = classroomDAO;
    }

    @Override
    public void createInscription(InscriptionRequest request) {
        // Validazione: utente esiste
        User user = userDAO.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Validazione: aula esiste
        Classroom classroom = classroomDAO.findById(request.getClassroomId())
                .orElseThrow(() -> new RuntimeException("Classroom not found"));

        // Creazione oggetto Inscription
        Inscription inscription = new Inscription();
        InscriptionId id = new InscriptionId(user.getId(), classroom.getId());
        inscription.setId(id);
        inscription.setUser(user);
        inscription.setClassroom(classroom);
        inscription.setRegistration(true);

        inscriptionDAO.create(inscription);

        log.info("User {} successfully registered to classroom {}", user.getId(), classroom.getId());
    }




    @Override
    public void unregisterUserFromClassroom(Long userId, Long classroomId) {
        inscriptionDAO.delete(userId, classroomId);
    }



}
