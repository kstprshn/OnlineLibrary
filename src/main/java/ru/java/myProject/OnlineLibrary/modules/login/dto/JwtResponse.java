package ru.java.myProject.OnlineLibrary.modules.login.dto;

import lombok.NoArgsConstructor;


@NoArgsConstructor
public class JwtResponse {

    private String token;

    public JwtResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}