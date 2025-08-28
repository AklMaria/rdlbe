package com.rdlbe.application.business.publishing;

import com.rdlbe.application.views.InscriptionRequest;

public interface InscriptionService {
    void createInscription(InscriptionRequest request);
    void unregisterUserFromClassroom(Long userId, Long classroomId);

}
