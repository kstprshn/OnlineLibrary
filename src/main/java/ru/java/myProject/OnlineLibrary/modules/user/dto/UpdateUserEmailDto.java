package ru.java.myProject.OnlineLibrary.modules.user.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserEmailDto {
    @NotBlank(message = "Username must be entered")
    private String username;
    @NotEmpty(message = "New email cannot be empty")
    @Email(message = "Invalid email format")
    @Pattern(regexp = "^[\\w.%+-]+@(mail\\.ru|gmail\\.com|yandex\\.ru)$", message = "Email must end with mail.ru, gmail.com, or yandex.ru")
    @Size(max = 20, message = "Email cannot be longer than 20 characters")
    private String newEmail;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }
}