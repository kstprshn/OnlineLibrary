package ru.java.myProject.OnlineLibrary.modules.author.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
public class AuthorRequestDto {

    @NotEmpty(message = "FIO must not be empty")
    @NotNull(message = "FIO is required")
    @Size(min = 2, max = 30, message = "FIO length must be between 2 and 30 characters")
    private String fio;

    @NotNull(message = "Birthday is required")
    @PastOrPresent(message = "Birthday must be a past or present date")
    private Date birthday;


    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
