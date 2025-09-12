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

        String storedHash = userDAO.decryptPwd(user.getId());

        if (passwordEncoder.matches(plainPassword, storedHash)) {
            UserItem userItem = modelMapper.map(user, UserItem.class);
            return Optional.of(userItem);
        }

        return Optional.empty();
    }

}