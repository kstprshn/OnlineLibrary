package ru.java.myProject.OnlineLibrary.modules.admin.service;

import ru.java.myProject.OnlineLibrary.modules.user.entity.User;

import java.util.List;

public interface AdminService {

    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByEmail(String email);

    void banUser(Long userId);

    void unbanUser(Long userId);

    void changeUserRole(Long userId);
}
