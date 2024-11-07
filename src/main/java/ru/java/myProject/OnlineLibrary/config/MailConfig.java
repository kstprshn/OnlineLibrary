package ru.java.myProject.OnlineLibrary.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${spring.mail.username}")
    private String mailUsername;

    @Value("${spring.mail.password}")
    private String mailPassword;

    @Bean
    public JavaMailSender getJavaMailSender() {
        return getJavaMailSender("gmail.com"); // По умолчанию используется Gmail
    }

    public JavaMailSender getJavaMailSender(String emailDomain) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        switch (emailDomain) {
            case "gmail.com" -> {
                mailSender.setHost("smtp.gmail.com");
                mailSender.setPort(587);
            }
            case "mail.ru" -> {
                mailSender.setHost("smtp.mail.ru");
                mailSender.setPort(587);
            }
            case "yandex.ru" -> {
                mailSender.setHost("smtp.yandex.ru");
                mailSender.setPort(465);
            }
            default -> throw new IllegalArgumentException("Unsupported email domain: " + emailDomain);
        }

        mailSender.setUsername(mailUsername);
        mailSender.setPassword(mailPassword);

        // Настройки  SMTP
        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.starttls.required", "true");

        return mailSender;
    }
}
