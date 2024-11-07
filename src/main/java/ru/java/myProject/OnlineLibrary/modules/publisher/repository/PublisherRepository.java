package ru.java.myProject.OnlineLibrary.modules.publisher.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.java.myProject.OnlineLibrary.modules.publisher.entity.Publisher;

import java.util.List;
import java.util.Optional;

@Repository
@RepositoryRestResource
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    @Query("SELECT p.name FROM Publisher p WHERE p.id = :id")
    Optional<String> findNameById(@Param("id") Long id);

    @Query("SELECT COUNT(p) > 0 FROM Publisher p WHERE p.name = :name")
    boolean existsByNameIgnoreCase(String name);

    @Modifying
    @Query("UPDATE Publisher p SET p.name = :name WHERE p.id = :id")
    void updateNameById(@Param("id") Long id, @Param("name") String name);

    List<Publisher> findByNameContainingIgnoreCaseOrderByName(String name);

}
