package ru.java.myProject.OnlineLibrary.modules.user.controller;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.java.myProject.OnlineLibrary.modules.login.dto.JwtResponse;
import ru.java.myProject.OnlineLibrary.modules.user.dto.mapper.UserMapper;
import ru.java.myProject.OnlineLibrary.modules.user.entity.User;
import ru.java.myProject.OnlineLibrary.config.JwtTokenProvider;
import ru.java.myProject.OnlineLibrary.modules.user.dto.*;
import ru.java.myProject.OnlineLibrary.modules.user.service.UserServiceImpl;
import ru.java.myProject.OnlineLibrary.util.ErrorResponse;
import ru.java.myProject.OnlineLibrary.modules.user.validation.UserDataUpdateValidator;

import java.security.Principal;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userServiceImpl;
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDataUpdateValidator userDataUpdateValidator;


    @PostMapping("/register")
    public ResponseEntity<JwtResponse> registerUser(@RequestBody @Valid UserRegistrationDto userDto) throws MessagingException {
        User newUser = userServiceImpl.registerUser(userMapper.convert(userDto));
        String token = jwtTokenProvider.generateToken(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new JwtResponse(token));
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        boolean verified = userServiceImpl.verifyUser(token);
        if (verified) {
            return ResponseEntity.status(HttpStatus.OK).body("Email verified successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or already verified token.");
        }
    }

    @PutMapping("/update-username")
    public ResponseEntity<String> updateUsername(@RequestBody UpdateUserUsernameDto updUserUsernameDto,
                                                            BindingResult bindingResult, Principal principal) {

        userDataUpdateValidator.validate(updUserUsernameDto, bindingResult);
        if (bindingResult.hasErrors()) {
            String errorMessages = ErrorResponse.getErrorMessages(bindingResult);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
        }
        User updatedUser = userServiceImpl.updateUsername(
                (User) userServiceImpl.loadUserByUsername(updUserUsernameDto.getCurrentUsername()),
                updUserUsernameDto.getNewUsername(), principal);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Username is successfully updated: " + updatedUser.getUsername());
    }

    @PutMapping("/update-email")
    public ResponseEntity<String> updateEmail(@RequestBody UpdateUserEmailDto updateUserEmailDto,
                                                            BindingResult bindingResult, Principal principal) {
        userDataUpdateValidator.validate(updateUserEmailDto, bindingResult);
        if (bindingResult.hasErrors()) {
            String errorMessages = ErrorResponse.getErrorMessages(bindingResult);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
        }
        User updatedUser = userServiceImpl.updateEmail(
                (User) userServiceImpl.loadUserByUsername(updateUserEmailDto.getUsername()),
                updateUserEmailDto.getNewEmail(), principal);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Email is successfully updated: " + updatedUser.getEmail());
    }

    @PutMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestBody UpdateUserPasswordDto updUserPasswordDto,
                                                            BindingResult bindingResult, Principal principal) {
        userDataUpdateValidator.validate(updUserPasswordDto, bindingResult);
        if (bindingResult.hasErrors()) {
            String errorMessages = ErrorResponse.getErrorMessages(bindingResult);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
        }
        userServiceImpl.updatePassword(
                (User) userServiceImpl.loadUserByUsername(updUserPasswordDto.getUsername()),
                updUserPasswordDto.getNewPassword(), principal);
        return ResponseEntity.status(HttpStatus.OK).body("Password is successfully updated.");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteUser(@RequestBody DeleteUserDto deleteUserDto) {
        User userToDelete = (User) userServiceImpl.loadUserByUsername(deleteUserDto.getUsername());
        userServiceImpl.deleteUser(userToDelete);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }



}
