package com.rdlbe.application.business.publishing;

import com.rdlbe.application.views.UserInsertItem;
import com.rdlbe.application.views.UserItem;
import com.rdlbe.application.views.UserUpdateItem;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserItem> getAllUsers();
    Optional<UserItem> getUserById(Long id);
    UserItem createUser(UserInsertItem user);
    UserItem updateUser(Long id, UserUpdateItem user);
    void deleteUser(Long id);
}
