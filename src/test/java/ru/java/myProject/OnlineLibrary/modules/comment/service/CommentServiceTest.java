package ru.java.myProject.OnlineLibrary.modules.comment.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import ru.java.myProject.OnlineLibrary.modules.publisher.entity.Publisher;
import ru.java.myProject.OnlineLibrary.modules.publisher.repository.PublisherRepository;
import ru.java.myProject.OnlineLibrary.modules.publisher.service.PublisherServiceImpl;
import ru.java.myProject.OnlineLibrary.util.EntityAlreadyExistsException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private PublisherServiceImpl publisherService;

    @Test
    public void getAll_ShouldReturnListOfPublishers() {
        List<Publisher> publishers = Arrays.asList(new Publisher(), new Publisher());
        Mockito.when(publisherRepository.findAll()).thenReturn(publishers);

        List<Publisher> result = publisherService.getAll(Sort.by(Sort.Order.asc("name")));

        assertEquals(2, result.size());
    }

    @Test
    public void get_ShouldReturnPublisher() {
        Publisher publisher = new Publisher();
        publisher.setId(1L);
        Mockito.when(publisherRepository.findById(1L)).thenReturn(Optional.of(publisher));

        Publisher result = publisherService.get(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void save_ShouldThrowExceptionIfPublisherExists() {
        Publisher publisher = new Publisher();
        publisher.setName("Existing Publisher");
        Mockito.when(publisherRepository.existsByNameIgnoreCase("Existing Publisher")).thenReturn(true);

        assertThrows(EntityAlreadyExistsException.class, () -> publisherService.save(publisher));
    }

    @Test
    public void delete() {
        Publisher publisher = new Publisher();
        publisher.setId(1L);
        Mockito.when(publisherRepository.findById(1L)).thenReturn(Optional.of(publisher));

        publisherService.delete(1L);

        Mockito.verify(publisherRepository).deleteById(1L);
    }

}