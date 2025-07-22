package com.rdlbe.application.business.internal.dao.presentation;

import com.rdlbe.application.business.internal.domains.Classroom;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassroomDAO {

    Optional<Classroom> getClassroomById(Long classroomId);

}
