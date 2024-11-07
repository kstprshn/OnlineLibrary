package ru.java.myProject.OnlineLibrary.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.stream.Collectors;

public class ErrorResponse {
    public static String getErrorMessages(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(", "));
    }
}
