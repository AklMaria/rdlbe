package com.rdlbe.application.business.publishing;

import com.rdlbe.application.views.UserItem;
import com.rdlbe.application.views.UserRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserItem> getAllUsers();
    Optional<UserItem> getUserById(Long id);
    UserItem createUser(UserRequest user);
    UserItem updateUser(Long id, UserRequest user);
    void deleteUser(Long id);
}
