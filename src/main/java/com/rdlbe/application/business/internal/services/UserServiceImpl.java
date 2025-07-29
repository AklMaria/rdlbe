package com.rdlbe.application.business.internal.services;

import com.rdlbe.application.business.internal.dao.presentation.UserDAO;
import com.rdlbe.application.business.internal.domains.Classroom;
import com.rdlbe.application.business.internal.domains.User;
import com.rdlbe.application.business.publishing.UserService;
import com.rdlbe.application.views.ClassroomItem;
import com.rdlbe.application.views.ClassroomRequest;
import com.rdlbe.application.views.UserItem;
import com.rdlbe.application.views.UserRequest;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserDAO userDAO, ModelMapper modelMapper) {
        this.userDAO = userDAO;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    private void init() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        // Configurazioni per mappare correttamente
        // Mappature necessarie
        modelMapper.typeMap(UserRequest.class, User.class);  // DTO → Entity
        modelMapper.typeMap(User.class, UserItem.class);     // Entity → DTO
    }

    @Override
    public List<UserItem> getAllUsers() {
        return userDAO.find(null)
                .stream()
                .map(user -> modelMapper.map(user, UserItem.class))
                .toList();
    }

    @Override
    public Optional<UserItem> getUserById(Long id) {
        return userDAO.findById(id)
                .map(user -> modelMapper.map(user, UserItem.class));
    }

    @Override
    public UserItem createUser(UserRequest userDto) {
        User user = modelMapper.map(userDto, User.class);
        // Log di debug per controllare i valori mappati
        log.info("Mapped User before insert: {}", user);
        Long id = userDAO.create(user);
        user.setId(id);
        return modelMapper.map(user, UserItem.class);
    }

    @Override
    public UserItem updateUser(Long id, UserRequest userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setId(id);
        userDAO.update(user);
        return modelMapper.map(user, UserItem.class);
    }

    @Override
    public void deleteUser(Long id) {
        userDAO.delete(id, null);
    }
}