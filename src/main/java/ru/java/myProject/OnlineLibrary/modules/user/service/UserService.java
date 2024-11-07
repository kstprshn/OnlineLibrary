package ru.java.myProject.OnlineLibrary.modules.user.service;

import jakarta.mail.MessagingException;
import ru.java.myProject.OnlineLibrary.modules.user.entity.User;

import java.security.Principal;


public interface UserService {

    User registerUser(User user) throws MessagingException;

    boolean verifyUser(String token);

    User updateUsername(User updatingUser, String username, Principal principal);

    User updatePassword(User updatingUser, String rawPassword, Principal principal);

    User updateEmail(User updatingUser, String email, Principal principal);

    void deleteUser(User user);
}
