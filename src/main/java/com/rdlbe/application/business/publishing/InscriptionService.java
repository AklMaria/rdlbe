package com.rdlbe.application.business.publishing;

import com.rdlbe.application.views.ClassroomItem;
import com.rdlbe.application.views.InscriptionRequest;

import java.util.List;

public interface InscriptionService {
    void createInscription(InscriptionRequest request);
    void unregisterUserFromClassroom(Long userId, Long classroomId);


}
