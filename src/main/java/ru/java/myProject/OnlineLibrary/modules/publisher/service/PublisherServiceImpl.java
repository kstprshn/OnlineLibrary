package ru.java.myProject.OnlineLibrary.modules.publisher.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.java.myProject.OnlineLibrary.modules.publisher.entity.Publisher;
import ru.java.myProject.OnlineLibrary.modules.publisher.repository.PublisherRepository;
import ru.java.myProject.OnlineLibrary.util.EntityAlreadyExistsException;
import ru.java.myProject.OnlineLibrary.util.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    @Autowired
    public PublisherServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public List<Publisher> getAll(Sort sort) {
        log.info("Searching all publishers with sort: {}", sort);
        return publisherRepository.findAll(sort);
    }


    @Override
    public List<Publisher> search(String... stringToFind) {
        log.info("Searching publishers by name part: {}", (Object) stringToFind);
        return publisherRepository.findByNameContainingIgnoreCaseOrderByName(stringToFind[0]);
    }

    @Override
    public Publisher get(long id) {
        log.info("Searching publisher with id: {}", id);
        return publisherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Publisher with id " + id + " not found"));
    }

    @Override
    @Transactional
    public Publisher save(Publisher publisher) {
        if (publisherRepository.existsByNameIgnoreCase(publisher.getName())) {
            log.error("Publisher with name {} already exists", publisher.getName());
            throw new EntityAlreadyExistsException("Publisher with name " + publisher.getName() + " already exists");
        }
        log.info("Saving new publisher: {}", publisher);
        return publisherRepository.save(publisher);
    }


    @Override
    @Transactional
    public void update(long id, String newName) {
        log.info("Updating publisher with id: {}", id);
        publisherRepository.updateNameById(id, newName);
    }

    @Override
    public Optional<String> findNameById(Long id) {
        log.info("Searching publisher with id: {}", id);
        return publisherRepository.findNameById(id);
    }

    @Override
    @Transactional
    public void delete(long id) {
        publisherRepository.deleteById(id);
        log.info("Publisher with id: {} successfully deleted", id);
    }
}
