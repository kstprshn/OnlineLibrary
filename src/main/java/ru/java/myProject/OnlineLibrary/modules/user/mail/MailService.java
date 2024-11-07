package ru.java.myProject.OnlineLibrary.modules.user.mail;

import jakarta.mail.MessagingException;
import ru.java.myProject.OnlineLibrary.modules.user.entity.User;

public interface MailService {

    void sendVerificationEmail(User user) throws MessagingException;
}
