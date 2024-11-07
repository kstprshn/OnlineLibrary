package ru.java.myProject.OnlineLibrary.modules.publisher.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PublisherRequestDto {

    @NotNull(message = "Name is required")
    @NotEmpty(message = "Name must not be empty")
    @Size(min = 2, max = 20, message = "Publisher name length must be between 2 and 20 characters")
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
