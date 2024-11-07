package ru.java.myProject.OnlineLibrary.modules.admin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.java.myProject.OnlineLibrary.modules.user.entity.User;
import ru.java.myProject.OnlineLibrary.roles.UserRole;
import ru.java.myProject.OnlineLibrary.modules.user.repository.UserRepository;
import ru.java.myProject.OnlineLibrary.modules.admin.exception.UserNotFoundException;

import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    @Autowired
    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        log.info("{} users found.", allUsers.size());
        return allUsers;
    }
    @Override
    public User getUserById(Long id) {
        log.info("User with ID '{}' found.", id);
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found."));
    }
    @Override
    public User getUserByEmail(String email) {
        log.info("User with email '{}' found.", email);
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User not found."));
    }
    @Transactional
    @Override
    public void banUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found."));

        if (user.isAdmin()) {
            throw new IllegalStateException("Admin cannot be banned!");
        }

        user.setBanned(true);
        log.info("User with ID {} has been banned.", userId);
        userRepository.save(user);
    }
    @Override
    @Transactional
    public void unbanUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setBanned(false);
        log.info("User with ID {} has been unbanned.", userId);
        userRepository.save(user);

    }
    @Override
    @Transactional
    public void changeUserRole(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.getUserRoles().clear();
        user.getUserRoles().add(UserRole.ROLE_ADMIN);
        log.info("User with ID '{}' appointed as an administrator.", userId);
        userRepository.save(user);
    }
}
