package com.rdlbe.application.business.internal.dao.presentation;

import com.rdlbe.application.business.internal.domains.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscriptionDAO {
    List<User> findUsersByClassroom(Long classroomId);
}
