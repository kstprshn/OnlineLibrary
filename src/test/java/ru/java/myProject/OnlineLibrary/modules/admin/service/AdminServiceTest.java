package ru.java.myProject.OnlineLibrary.modules.admin.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.java.myProject.OnlineLibrary.modules.user.entity.User;
import ru.java.myProject.OnlineLibrary.modules.user.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private AdminServiceImpl adminService;


    @Test
    public void getAllUsers_ShouldReturnListOfUsers() {
        List<User> users = Arrays.asList(new User(), new User());
        Mockito.when(userRepository.findAll()).thenReturn(users);

        List<User> result = adminService.getAllUsers();

        assertEquals(2, result.size());
    }

    @Test
    public void getUserById_ShouldReturnUser() {
        User user = new User();
        user.setId(1L);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = adminService.getUserById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void banUser_ShouldThrowExceptionIfAdmin() {
        User user = new User();
        user.setId(1L);
        user.setAdmin(true);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertThrows(IllegalStateException.class, () -> adminService.banUser(1L));
    }
}