package ru.java.myProject.OnlineLibrary.modules.login.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.java.myProject.OnlineLibrary.modules.login.dto.JwtResponse;
import ru.java.myProject.OnlineLibrary.modules.login.dto.LoginDto;
import ru.java.myProject.OnlineLibrary.modules.login.service.LoginServiceImpl;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication API", description = "Endpoints for user authentication")
public class AuthController {

    private final LoginServiceImpl loginServiceImpl;

    @PostMapping("/login")
    @Operation(summary = "User login")
    public ResponseEntity<JwtResponse> login(@RequestBody @Valid LoginDto loginDto) {
        String token = loginServiceImpl.login(loginDto.getUsername(), loginDto.getPassword());
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
