package ru.java.myProject.OnlineLibrary.modules.user.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.java.myProject.OnlineLibrary.modules.user.dto.UpdateUserEmailDto;
import ru.java.myProject.OnlineLibrary.modules.user.dto.UpdateUserPasswordDto;
import ru.java.myProject.OnlineLibrary.modules.user.dto.UpdateUserUsernameDto;

@Component
public class UserDataUpdateValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return  UpdateUserEmailDto.class.equals(clazz) ||
                UpdateUserPasswordDto.class.equals(clazz) ||
                UpdateUserUsernameDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        if (target instanceof UpdateUserEmailDto) {
            validateEmail((UpdateUserEmailDto) target, errors);
        } else if (target instanceof UpdateUserPasswordDto) {
            validatePassword((UpdateUserPasswordDto) target, errors);
        } else if (target instanceof UpdateUserUsernameDto) {
            validateUsername((UpdateUserUsernameDto) target, errors);
        }
    }
    private void validateEmail(UpdateUserEmailDto updateEmailDto, Errors errors) {

        if (updateEmailDto.getUsername() == null || updateEmailDto.getUsername().isEmpty()) {
            errors.rejectValue("username", "", "Username cannot be empty");
        }
        if (updateEmailDto.getNewEmail() == null || !updateEmailDto.getNewEmail().contains("@")) {
            errors.rejectValue("email", "", "Email should be valid");
        }
    }

    private void validateUsername(UpdateUserUsernameDto updateUsernameDto, Errors errors) {

        if (updateUsernameDto.getCurrentUsername() == null || updateUsernameDto.getCurrentUsername().isEmpty()) {
            errors.rejectValue("username", "", "Username cannot be empty");
        }
        if (updateUsernameDto.getNewUsername() == null || updateUsernameDto.getNewUsername().isEmpty()) {
            errors.rejectValue("username", "", "Your new username is not valid");
        }
    }

    private void validatePassword(UpdateUserPasswordDto updatePasswordDto, Errors errors) {

        if (updatePasswordDto.getUsername() == null || updatePasswordDto.getUsername().isEmpty()) {
            errors.rejectValue("username", "", "Username cannot be empty");
        }
        if (updatePasswordDto.getNewPassword().toCharArray().length < 8 || updatePasswordDto.getNewPassword() == null) {
            errors.rejectValue("password", "", "Your new password is not valid");
        }
    }
}
