package ru.java.myProject.OnlineLibrary.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.java.myProject.OnlineLibrary.modules.user.service.UserServiceImpl;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final static String[] PERMITTED_ENDPOINTS = {
            "/api/auth/**",
            "/api/users/register",
            "/api/book/**",
            "/api/genre/allGenres",
            "/api/genre/search",
            "/api/author/search",
            "/api/author/showBooksByAuthor/{authorId}"
    };
    private final JwtTokenFilter jwtTokenFilter;

    private final UserServiceImpl userService;

    @Autowired
    public SecurityConfig(JwtTokenFilter jwtTokenFilter, UserServiceImpl userService) {
        this.jwtTokenFilter = jwtTokenFilter;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> requests

                        .requestMatchers(PERMITTED_ENDPOINTS).permitAll()
                        .requestMatchers("/api/admin/**", "/api/admin/statistics/**").hasRole("ADMIN")
                        .requestMatchers("/api/publishers/**").hasRole("ADMIN")
                        .requestMatchers("/api/author/findAll", "/api/author/findAuthor/{author_id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/genre/findOne/{genre_id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/comments/getComment/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/book/create").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/genre/create").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/author/create").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/book/redact/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/book/delete/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/genre/delete/{genre_id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/author/delete/{author_id}").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
