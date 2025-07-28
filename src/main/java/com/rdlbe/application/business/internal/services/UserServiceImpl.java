package com.rdlbe.application.business.internal.services;

import com.rdlbe.application.business.internal.dao.presentation.UserDAO;
import com.rdlbe.application.business.internal.domains.User;
import com.rdlbe.application.business.publishing.UserService;
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
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.typeMap(User.class, UserItem.class);
    }

    @Override
    public List<UserItem> getAllUsers() {
        return List.of();
    }

    @Override
    public Optional<UserItem> getUserById(Long id) {
        return Optional.empty();
    }

    @Override
    public UserItem createUser(UserRequest user) {
        return null;
    }

    @Override
    public UserItem updateUser(Long id, UserRequest user) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }
}
