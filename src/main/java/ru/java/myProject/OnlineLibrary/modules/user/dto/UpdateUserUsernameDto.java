package ru.java.myProject.OnlineLibrary.modules.user.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserUsernameDto {
    @NotBlank(message = "Username must be entered")
    private String currentUsername;
    @NotEmpty(message = "New username cannot be empty")
    @Size(max = 20, message = "New username is too long ")
    private String newUsername;

    public String getCurrentUsername() {
        return currentUsername;
    }

    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    public String getNewUsername() {
        return newUsername;
    }

    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }
}
