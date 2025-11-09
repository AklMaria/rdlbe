package com.rdlbe.application.api.controllers;

import com.rdlbe.application.business.publishing.ClassroomService;
import com.rdlbe.application.views.ClassroomItem;
import com.rdlbe.application.views.ClassroomRequest;
import com.rdlbe.application.views.ClassroomUsersDetailsItem;
import com.rdlbe.application.views.DocumentItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/classrooms")
@Slf4j
public class ClassroomRestController {

    private final ClassroomService classroomService;

    public ClassroomRestController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    // --- GET: tutte le classrooms ---
    @GetMapping
    public List<ClassroomItem> getAllClassrooms() {
        log.debug("Getting all classrooms");
        return classroomService.getAllClassrooms();
    }

    @GetMapping("/completed")
    public List<ClassroomItem> getCompletedClassrooms(@RequestParam("userId") Long userId) {
        log.debug("Getting completed classrooms");
        return classroomService.getCompletedClassrooms(userId);
    }

    // --- GET: classroom by ID ---
    @GetMapping("/{id}")
    public Optional<ClassroomItem> getClassroomById(@PathVariable("id") Long id) {
        return classroomService.getClassroomById(id);
    }

    // --- POST: crea nuova classroom ---
    @PostMapping
    public ClassroomItem createClassroom(@RequestBody ClassroomItem classroom) {
        log.debug("Creating new classroom: {}", classroom);
        return classroomService.createClassroom(classroom);
    }

    // --- PUT: aggiorna classroom esistente ---
    @PutMapping("/{id}")
    public ClassroomItem updateClassroom(@PathVariable("id") Long id,
                                         @RequestBody ClassroomRequest classroom) {
        return classroomService.updateClassroom(id, classroom);
    }

    // --- DELETE: elimina classroom ---
    @DeleteMapping("/{id}")
    public void deleteClassroom(@PathVariable("id") Long id) {
        classroomService.deleteClassroom(id);
    }

    @GetMapping("/{id}/users")
    public ClassroomUsersDetailsItem getClassroomUsers(@PathVariable("id") Long id) {
        return classroomService.getClassroomUsersById(id);
    }

    @GetMapping("/{id}/docs")
    public List<DocumentItem> getClassroomDocs(@PathVariable("id") Long id ) {
        return classroomService.getClassroomDocs(id);
    }


    // NUOVO ENDPOINT DI DOWNLOAD documento
    @GetMapping("/document/download/{id}")
    public ResponseEntity<Resource> downloadDocumentClassroom(@PathVariable Long id) {
        DocumentItem document = classroomService.findById(id);
        if (document == null) {
            return ResponseEntity.notFound().build();
        }

        Resource fileResource = classroomService.getDocumentAsResource(document);
        if (fileResource == null || !fileResource.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(document.getContentType()))
                .body(fileResource);
    }




    @PostMapping(path="/{id}/doc", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadDoc(
            @RequestParam("id") Long id,
            @RequestParam("file") MultipartFile file
    ) {
        log.info("Ricevuto upload '{}' per classroom id {}", file.getOriginalFilename(), id);
        classroomService.uploadDoc(id, file);
    }

    @DeleteMapping("/{id}/docs/{docId}")
    public void deleteDoc(@PathVariable("id") Long id, @PathVariable("docId") Long docId) {
        classroomService.deleteDoc(id, docId);
    }

}
