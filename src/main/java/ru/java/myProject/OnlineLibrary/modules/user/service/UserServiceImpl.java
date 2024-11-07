package ru.java.myProject.OnlineLibrary.modules.user.service;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.java.myProject.OnlineLibrary.modules.user.mail.MailService;
import ru.java.myProject.OnlineLibrary.modules.user.entity.User;
import ru.java.myProject.OnlineLibrary.roles.UserRole;
import ru.java.myProject.OnlineLibrary.modules.user.repository.UserRepository;
import ru.java.myProject.OnlineLibrary.modules.user.exception.EmailAlreadyExistsException;
import ru.java.myProject.OnlineLibrary.modules.user.exception.UsernameAlreadyExistsException;
import ru.java.myProject.OnlineLibrary.util.EntityNotFoundException;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MailService mailService;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, MailService mailService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    @Transactional
    @Override
    public User registerUser(User user) throws MessagingException {

        if (userRepository.existsByUsername(user.getUsername())) {
            log.warn("Username '{}' already exists", user.getUsername());
            throw new UsernameAlreadyExistsException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            log.warn("Email '{}' already exists", user.getEmail());
            throw new EmailAlreadyExistsException("Email already exists");
        }
        user.setBanned(false);
        user.getUserRoles().add(UserRole.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setVerificationToken(UUID.randomUUID().toString());
        user.setVerified(false);

        User savedUser = userRepository.save(user);
        mailService.sendVerificationEmail(savedUser);
        log.info("User: {} is registering.", user.getUsername());
        return savedUser;

    }

    @Override
    @Transactional
    public boolean verifyUser(String token) {
        User user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Invalid verification token"));

        if (user.isVerified()) {
            return false; // Уже подтвержден
        }

        user.setVerified(true);
        user.setVerificationToken(null);
        userRepository.save(user);
        return true;
    }

    @Transactional
    @Override
    public User updateUsername(User updatingUser, String updatingUsername, Principal principal) {
        if (!updatingUser.getUsername().equals(principal.getName())) {
            throw new SecurityException("Access to the data update is forbidden.");
        }
        if (userRepository.existsByUsername(updatingUsername)) {
            log.warn("Username '{}' already taken", updatingUsername);
            throw new UsernameAlreadyExistsException("Username already taken.");
        }
        updatingUser.setUsername(updatingUsername);
        log.info("Updating username for user with id: {}, new username: {}", updatingUser.getId(), updatingUsername);
        return userRepository.save(updatingUser);
    }

    @Transactional
    @Override
    public User updatePassword(User updatingUser, String rawPassword, Principal principal) {
        if (!updatingUser.getUsername().equals(principal.getName())) {
            throw new SecurityException("Access to the data update is forbidden.");
        }
        String encodedPassword = passwordEncoder.encode(rawPassword);
        updatingUser.setPassword(encodedPassword);
        log.info("Password successfully updated for user with id: {}", updatingUser.getId());
        return userRepository.save(updatingUser);
    }

    @Transactional
    @Override
    public User updateEmail(User updatingUser, String updatingEmail, Principal principal) {

        if (!updatingUser.getUsername().equals(principal.getName())) {
            throw new SecurityException("Access to the data update is forbidden.");
        }
        if (userRepository.existsByEmail(updatingEmail)) {
            log.warn("Email '{}' already in use", updatingEmail);
            throw new EmailAlreadyExistsException("Email already in use.");
        }
        updatingUser.setEmail(updatingEmail);
        log.info("Updating email for user with id: {}, new email: {}", updatingUser.getId(), updatingEmail);

        return userRepository.save(updatingUser);
    }

    @Transactional
    @Override
    public void deleteUser(User user) {
        userRepository.deleteById(user.getId());
        log.info("User with id: {} successfully deleted.", user.getId());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username));
        if (user.isEmpty()){
            log.warn("User with username '{}' not found", username);
            throw new UsernameNotFoundException("Can't find user with username: " + username);
        }
        log.info("User with username '{}' successfully loaded", username);
        return user.get();
    }
}
