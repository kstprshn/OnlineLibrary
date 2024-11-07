package ru.java.myProject.OnlineLibrary.modules.user.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {

    @NotBlank(message = "Username must be entered")
    private String username;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    @Pattern(regexp = "^[\\w.%+-]+@(mail\\.ru|gmail\\.com|yandex\\.ru)$", message = "Email must end with mail.ru, gmail.com, or yandex.ru")
    @Size(max = 20, message = "Email cannot be longer than 20 characters")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
