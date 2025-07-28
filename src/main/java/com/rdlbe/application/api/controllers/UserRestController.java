package com.rdlbe.application.api.controllers;

import com.rdlbe.application.business.publishing.UserService;
import com.rdlbe.application.views.UserItem;
import com.rdlbe.application.views.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserRestController {



        private final UserService userService;

        public UserRestController(UserService userService) {
            this.userService = userService;
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
}

