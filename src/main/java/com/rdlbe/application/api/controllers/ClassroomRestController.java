package com.rdlbe.application.api.controllers;

import com.rdlbe.application.business.publishing.ClassroomService;
import com.rdlbe.application.views.ClassroomItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/classrooms")
@Slf4j
public class ClassroomRestController {

    private final ClassroomService classroomService;

    public ClassroomRestController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @GetMapping
    public List<ClassroomItem> findClassrooms() {
        return classroomService.findClassrooms();
    }

    @GetMapping("/{id}")
    public ClassroomItem findClassroomById(@PathVariable("id") long id) {
        return classroomService.findClassroomById(id);
    }

}
