package ru.java.myProject.OnlineLibrary.modules.publisher.service;

import org.springframework.data.domain.Sort;
import ru.java.myProject.OnlineLibrary.modules.publisher.entity.Publisher;

import java.util.List;
import java.util.Optional;


public interface PublisherService{

    List<Publisher> getAll(Sort sort);

    List<Publisher> search(String... stringToFind);

    Optional<String> findNameById(Long id);

    Publisher get(long id);

    Publisher save(Publisher p);

    void update(long id, String name);

    void delete(long id);
}
