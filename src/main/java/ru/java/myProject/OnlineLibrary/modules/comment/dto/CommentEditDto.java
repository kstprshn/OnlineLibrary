package ru.java.myProject.OnlineLibrary.modules.comment.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CommentEditDto {

    @NotEmpty(message = "New text must not be empty")
    @Size(min = 1, max = 100, message = "New text length must be between 1 and 100 characters")
    private String newText;

    public String getNewText() {
        return newText;
    }

    public void setNewText(String newText) {
        this.newText = newText;
    }
}