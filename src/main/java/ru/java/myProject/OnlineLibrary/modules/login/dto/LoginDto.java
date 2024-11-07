package ru.java.myProject.OnlineLibrary.modules.login.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    @NotBlank(message = "Username must be entered")
    private String username;
    @NotBlank(message = "Password must be entered")
    private String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}