package ru.java.myProject.OnlineLibrary.modules.user.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import ru.java.myProject.OnlineLibrary.roles.UserRole;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotEmpty
    String username;

    @NotEmpty
    String email;

    Set<UserRole> userRoles;

    @NotEmpty
    String password;

    boolean isBanned;

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

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }
}
