package ru.java.myProject.OnlineLibrary.modules.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.java.myProject.OnlineLibrary.modules.user.dto.UserDto;
import ru.java.myProject.OnlineLibrary.modules.admin.dto.UserEmailDto;
import ru.java.myProject.OnlineLibrary.modules.user.dto.mapper.UserMapper;
import ru.java.myProject.OnlineLibrary.modules.user.entity.User;
import ru.java.myProject.OnlineLibrary.modules.admin.service.AdminServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@Tag(name = "Admin API", description = "Methods for user administration")
public class AdminController {

    private final AdminServiceImpl adminServiceImpl;
    private final UserMapper userMapper;

    @Autowired
    public AdminController(AdminServiceImpl adminServiceImpl, UserMapper userMapper) {
        this.adminServiceImpl = adminServiceImpl;
        this.userMapper = userMapper;
    }

    @PutMapping("/{userId}/ban")
    @Operation(summary = "Ban a user")
    public ResponseEntity<Void> banUser(@PathVariable("userId") Long userId) {
        adminServiceImpl.banUser(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userId}/unban")
    @Operation(summary = "Unban a user")
    public ResponseEntity<Void> unbanUser(@PathVariable("userId") Long userId) {
        adminServiceImpl.unbanUser(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/role/{userId}")
    @Operation(summary = "Change the user's role")
    public ResponseEntity<Void> changeUserRole(@PathVariable("userId") Long userId) {
        adminServiceImpl.changeUserRole(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    @Operation(summary = "get all users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = adminServiceImpl.getAllUsers();
        return ResponseEntity.ok(users.stream().map(userMapper::convertToUserDto).collect(Collectors.toList()));
    }

    @GetMapping("/findUserById/{userId}")
    @Operation(summary = "Find a user by ID")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Long id) {
        User user = adminServiceImpl.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userMapper.convertToUserDto(user));
    }

    @GetMapping("/findUserByEmail")
    @Operation(summary = "Find a user by email")
    public ResponseEntity<UserDto> getUserByEmail(@RequestBody @Valid UserEmailDto userEmailDto) {
        User user = adminServiceImpl.getUserByEmail(userEmailDto.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(userMapper.convertToUserDto(user));
    }
}
