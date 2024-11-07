package ru.java.myProject.OnlineLibrary.modules.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.java.myProject.OnlineLibrary.modules.user.entity.User;

import java.util.Optional;

@Repository
@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    Optional<User> findByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findByVerificationToken(String token);
}