package ru.java.myProject.OnlineLibrary.modules.admin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UserEmailDto {

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    @Pattern(regexp = "^[\\w.%+-]+@(mail\\.ru|gmail\\.com|yandex\\.ru)$", message = "Email must end with mail.ru, gmail.com, or yandex.ru")
    @Size(max = 20, message = "Email cannot be longer than 20 characters")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}