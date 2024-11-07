package ru.java.myProject.OnlineLibrary.modules.login.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.java.myProject.OnlineLibrary.config.JwtTokenProvider;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public String login(String username, String password){
        log.info("User attempting login: {}", username);
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            log.info("User {} authenticated successfully", username);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return jwtTokenProvider.generateToken(userDetails);
        }catch (BadCredentialsException e) {
            log.error("Invalid login attempt for user: {}", username);
            throw new BadCredentialsException("Your username or password is incorrect");
        }
    }
}