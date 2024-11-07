package ru.java.myProject.OnlineLibrary.modules.admin.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.java.myProject.OnlineLibrary.modules.admin.service.AdminServiceImpl;
import ru.java.myProject.OnlineLibrary.modules.user.dto.UserDto;
import ru.java.myProject.OnlineLibrary.modules.user.dto.mapper.UserMapper;
import ru.java.myProject.OnlineLibrary.modules.user.entity.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private AdminServiceImpl adminService;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private AdminController adminController;


    @Test
    void testGetAllUsers() {
        User user = new User();
        UserDto userDto = new UserDto();
        List<User> users = List.of(user);
        List<UserDto> usersDtos = List.of(userDto);

        Mockito.when(adminService.getAllUsers()).thenReturn(users);
        Mockito.when(userMapper.convertToUserDto(user)).thenReturn(userDto);

        ResponseEntity<List<UserDto>> response = adminController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usersDtos, response.getBody());
        Mockito.verify(adminService).getAllUsers();
    }

    @Test
    void testGetUserById() {
        Long userId = 1L;
        User user = new User();
        UserDto userDto = new UserDto();

        Mockito.when(adminService.getUserById(userId)).thenReturn(user);
        Mockito.when(userMapper.convertToUserDto(user)).thenReturn(userDto);

        ResponseEntity<UserDto> response = adminController.getUserById(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto, response.getBody());
        Mockito.verify(adminService).getUserById(userId);
    }
}