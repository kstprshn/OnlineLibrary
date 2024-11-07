package ru.java.myProject.OnlineLibrary.modules.author.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {
    @NotEmpty
    @NotNull
    @Size(min = 2, max = 30, message = "FIO length must be between 2 and 30 characters")
    private String fio;

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }
}
