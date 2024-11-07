package ru.java.myProject.OnlineLibrary.modules.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserPasswordDto {
    @NotBlank(message = "Username must be entered")
    private String username;
    @NotEmpty(message = "New password cannot be empty")
    @Size(min = 8, max = 20, message = "New password must be between 8 and 20 characters")
    private String newPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
