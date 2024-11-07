package ru.java.myProject.OnlineLibrary.modules.user.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.java.myProject.OnlineLibrary.modules.user.entity.User;

@Service
@Transactional
public class MailServiceImpl implements MailService {

    @Value("${spring.mail.username}")
    private String mailUsername;
    private final JavaMailSender mailSender;


    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(User user) {

        String verificationUrl = "http://localhost:8080/api/users/verify?token=" + user.getVerificationToken();
        String message = "Dear " + user.getUsername() + ",\nPlease click the link below to verify your email:\n" + verificationUrl;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setFrom(mailUsername);
        email.setSubject("Email Verification");
        email.setText(message);
        mailSender.send(email);
    }
}