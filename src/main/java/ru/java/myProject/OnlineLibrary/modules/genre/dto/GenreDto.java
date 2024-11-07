package ru.java.myProject.OnlineLibrary.modules.genre.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class GenreDto {

    @NotEmpty(message = "Name must not be empty")
    @Size(min = 3, max = 20, message = "Genre name length must be between 3 and 20 characters")
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
