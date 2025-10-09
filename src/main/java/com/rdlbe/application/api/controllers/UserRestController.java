package com.rdlbe.application.api.controllers;

import com.rdlbe.application.business.publishing.ClassroomService;
import com.rdlbe.application.business.publishing.UserService;
import com.rdlbe.application.views.ClassroomItem;
import com.rdlbe.application.views.LoginRequest;
import com.rdlbe.application.views.UserItem;
import com.rdlbe.application.views.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserRestController {



        private final UserService userService;
        private final ClassroomService classroomService;

        public UserRestController(UserService userService, ClassroomService classroomService) {
            this.userService = userService;
            this.classroomService = classroomService;
        }

        // --- GET: tutti gli utenti ---
        @GetMapping
        public List<UserItem> getAllUsers() {
            return userService.getAllUsers();
        }

        // --- GET: utente per ID ---
        @GetMapping("/{id}")
        public Optional<UserItem> getUserById(@PathVariable("id") Long id) {
            return userService.getUserById(id);
        }

        @GetMapping(value = "/exist")
        public Long userExist(@RequestParam("email") String email) {
            Optional<UserItem> user = userService.getUserByEmail(email);
            if (user.isPresent()) {
                return user.get().getId();
            }
            return 0L;
        }

        // --- POST: crea utente ---
        @PostMapping
        public UserItem createUser(@RequestBody UserRequest user) {
            return userService.createUser(user);
        }

        // --- PUT: aggiorna utente ---
        @PutMapping("/{id}")
        public UserItem updateUser(@PathVariable("id") Long id,
                                   @RequestBody UserRequest user) {
            return userService.updateUser(id, user);
        }

        // --- DELETE: elimina utente ---
        @DeleteMapping("/{id}")
        public void deleteUser(@PathVariable("id") Long id) {
            userService.deleteUser(id);
        }



    @GetMapping("/{userId}/classrooms")
    public List<ClassroomItem> getClassroomsByUser(@PathVariable Long userId) {
        return classroomService.getClassroomsByUser(userId);
    }

    @PostMapping("/login")
    public ResponseEntity<UserItem> login(@RequestBody LoginRequest request) {
        return userService.login(request.getMail(), request.getPassword())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null));
    }

}

