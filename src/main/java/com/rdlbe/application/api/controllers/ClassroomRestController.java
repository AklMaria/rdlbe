package com.rdlbe.application.api.controllers;

import com.rdlbe.application.business.publishing.ClassroomService;
import com.rdlbe.application.views.ClassroomInsertItem;
import com.rdlbe.application.views.ClassroomItem;
import com.rdlbe.application.views.ClassroomUpdateItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
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

    // --- Query personalizzate ---

    // 1. Aule di un utente in una data
    @GetMapping("/by-user-date")
    public List<ClassroomItem> getClassroomsByUserAndDate(@RequestParam("userId") Long userId,
                                                          @RequestParam("date") String date) {
        LocalDateTime parsedDate = LocalDateTime.parse(date);
        return classroomService.getClassroomsByUserAndDate(userId, parsedDate);
    }

    // 2. Aule disponibili in una data
    @GetMapping("/available")
    public List<ClassroomItem> getAvailableClassroomsByDate(@RequestParam("date") String date) {
        LocalDateTime parsedDate = LocalDateTime.parse(date);
        return classroomService.getAvailableClassroomsByDate(parsedDate);
    }

    // 3. Aule di un utente in un intervallo di date
    @GetMapping("/by-user-daterange")
    public List<ClassroomItem> getClassroomsByUserInDateRange(@RequestParam("userId") Long userId,
                                                              @RequestParam("startDate") String startDate,
                                                              @RequestParam("endDate") String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        return classroomService.getClassroomsByUserInDateRange(userId, start, end);
    }

}
