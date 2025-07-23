package com.rdlbe.application.api.controllers;

import com.rdlbe.application.business.publishing.ClassroomService;
import com.rdlbe.application.views.ClassroomInsertItem;
import com.rdlbe.application.views.ClassroomItem;
import com.rdlbe.application.views.ClassroomUpdateItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
        return classroomService.getAllClassrooms();
    }

    // --- GET: classroom by ID ---
    @GetMapping("/{id}")
    public Optional<ClassroomItem> getClassroomById(@PathVariable("id") Long id) {
        return classroomService.getClassroomById(id);
    }

    // --- POST: crea nuova classroom ---
    @PostMapping
    public ClassroomItem createClassroom(@RequestBody ClassroomInsertItem classroom) {
        return classroomService.createClassroom(classroom);
    }

    // --- PUT: aggiorna classroom esistente ---
    @PutMapping("/{id}")
    public ClassroomItem updateClassroom(@PathVariable("id") Long id,
                                         @RequestBody ClassroomUpdateItem classroom) {
        return classroomService.updateClassroom(id, classroom);
    }

    // --- DELETE: elimina classroom ---
    @DeleteMapping("/{id}")
    public void deleteClassroom(@PathVariable("id") Long id) {
        classroomService.deleteClassroom(id);
    }

}
