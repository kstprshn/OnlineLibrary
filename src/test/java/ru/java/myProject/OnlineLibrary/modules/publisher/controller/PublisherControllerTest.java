package ru.java.myProject.OnlineLibrary.modules.publisher.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.java.myProject.OnlineLibrary.modules.publisher.dto.PublisherResponseDto;
import ru.java.myProject.OnlineLibrary.modules.publisher.dto.mapper.PublisherMapper;
import ru.java.myProject.OnlineLibrary.modules.publisher.entity.Publisher;
import ru.java.myProject.OnlineLibrary.modules.publisher.service.PublisherServiceImpl;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PublisherControllerTest {

    @Mock
    private PublisherServiceImpl publisherService;
    @Mock
    private PublisherMapper publisherMapper;
    @InjectMocks
    private PublisherController publisherController;


    @Test
    void testGetAllPublishers() {

        Publisher publisher1 = new Publisher(1L, "Publisher 1", null);
        Publisher publisher2 = new Publisher(2L, "Publisher 2", null);

        PublisherResponseDto publisherDto1 = new PublisherResponseDto(1L, "Publisher 1");
        PublisherResponseDto publisherDto2 = new PublisherResponseDto(2L, "Publisher 2");

        Mockito.when(publisherService.getAll(Mockito.any())).thenReturn(List.of(publisher1, publisher2));
        Mockito.when(publisherMapper.convertToDto(publisher1)).thenReturn(publisherDto1);
        Mockito.when(publisherMapper.convertToDto(publisher2)).thenReturn(publisherDto2);

        ResponseEntity<List<PublisherResponseDto>> response = publisherController.getAllPublishers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
        Mockito.verify(publisherService).getAll(Mockito.any());
    }

    @Test
    void testDeletePublisher() {
        Long publisherId = 1L;

        ResponseEntity<HttpStatus> response = publisherController.deletePublisher(publisherId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Mockito.verify(publisherService).delete(publisherId);
    }
}