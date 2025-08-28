package com.rdlbe.application.api.controllers;

import com.rdlbe.application.business.publishing.InscriptionService;
import com.rdlbe.application.views.InscriptionRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inscriptions")
@Slf4j
public class InscriptionRestController {

    private final InscriptionService inscriptionService;

    public InscriptionRestController(InscriptionService inscriptionService) {
        this.inscriptionService = inscriptionService;
    }

    // --- POST: iscrizione di un utente ad una classroom ---
    @PostMapping
    public void createInscription(@RequestBody InscriptionRequest request) {
        inscriptionService.createInscription(request);
    }

    @DeleteMapping
    public void unregisterUserFromClassroom(@RequestParam Long userId, @RequestParam Long classroomId) {
        inscriptionService.unregisterUserFromClassroom(userId, classroomId);
    }

}