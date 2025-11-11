package com.rdlbe.application.business.internal.services;

import com.rdlbe.application.business.internal.dao.presentation.UserDAO;
import com.rdlbe.application.business.internal.domains.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rdlbe.application.views.UserItem;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserDAO userDAO;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @PersistenceContext
    private final EntityManager entityManager;

    @Transactional(readOnly = true)
    public Optional<UserItem> checkCredentials(String email, String plainPassword) {
        Optional<User> userOptional = userDAO.findByEmail(email);

        if (userOptional.isEmpty()) {
            return Optional.empty();
        }

        User user = userOptional.get();

        // ✅ Leggi direttamente l’hash salvato come byte[]
        if (user.getPassword() == null) {
            return Optional.empty(); // nessuna password salvata
        }

        String storedHash = new String(user.getPassword(), StandardCharsets.UTF_8);
        System.out.println(">>> HASH dal DB: " + storedHash);
        System.out.println(">>> RAW password: " + plainPassword);
        System.out.println(">>> MATCH result: " + passwordEncoder.matches(plainPassword, new String(user.getPassword(), StandardCharsets.UTF_8)));


        // ✅ Confronta la password in chiaro con l’hash bcrypt
        if (passwordEncoder.matches(plainPassword, storedHash)) {
            UserItem userItem = modelMapper.map(user, UserItem.class);
            return Optional.of(userItem);
        }

        return Optional.empty();
    }


}