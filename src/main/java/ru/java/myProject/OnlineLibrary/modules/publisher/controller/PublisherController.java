package ru.java.myProject.OnlineLibrary.modules.publisher.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.java.myProject.OnlineLibrary.modules.publisher.dto.PublisherBooksDto;
import ru.java.myProject.OnlineLibrary.modules.publisher.dto.PublisherRequestDto;
import ru.java.myProject.OnlineLibrary.modules.publisher.dto.PublisherResponseDto;
import ru.java.myProject.OnlineLibrary.modules.publisher.dto.mapper.PublisherMapper;
import ru.java.myProject.OnlineLibrary.modules.publisher.entity.Publisher;
import ru.java.myProject.OnlineLibrary.modules.publisher.service.PublisherServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/publishers")
@Tag(name = "Publisher API", description = "Methods of publisher management")
public class PublisherController {

    private final PublisherServiceImpl publisherServiceImpl;
    private final PublisherMapper publisherMapper;

    @Autowired
    public PublisherController(PublisherServiceImpl publisherServiceImpl, PublisherMapper publisherMapper) {
        this.publisherServiceImpl = publisherServiceImpl;
        this.publisherMapper = publisherMapper;
    }

    @GetMapping("/findAll")
    @Operation(summary = "Get all publishers")
    public ResponseEntity<List<PublisherResponseDto>> getAllPublishers() {
        List<Publisher> allPublishers = publisherServiceImpl.getAll(Sort.by(Sort.Direction.ASC, "name"));
        List<PublisherResponseDto> publishersDto = allPublishers.stream()
                .map(publisherMapper::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok(publishersDto);
    }

    @GetMapping("/findOne/{publisher_id}")
    @Operation(summary = "Get publisher by ID")
    public ResponseEntity<PublisherBooksDto> getPublisherById(@PathVariable("publisher_id") Long id) {
        Publisher publisher = publisherServiceImpl.get(id);
        return publisher != null ? ResponseEntity.ok(publisherMapper.convert(publisher))
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Search publishers")
    public ResponseEntity<List<PublisherResponseDto>> searchPublishers(@RequestBody @NotEmpty String publisherName) {
        List<Publisher> publishers = publisherServiceImpl.search(publisherName);
        List<PublisherResponseDto> publishersDto = publishers.stream()
                .map(publisherMapper::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok(publishersDto);
    }

    @PostMapping("/create")
    @Operation(summary = "Create a publisher")
    public ResponseEntity<PublisherResponseDto> createPublisher(@RequestBody PublisherRequestDto publisherRequestDto) {
        Publisher publisherToSave = publisherServiceImpl.save(publisherMapper.convert(publisherRequestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(publisherMapper.convertToDto(publisherToSave));
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update the publisher")
    public ResponseEntity<HttpStatus> updatePublisher(@PathVariable("id") long id,
                                                      @RequestBody @Valid PublisherRequestDto publisherRequestDto) {
        Optional<String> existingName = publisherServiceImpl.findNameById(id);

        if (existingName.isPresent()) {
            publisherServiceImpl.update(id, publisherRequestDto.getName());
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{publisher_id}")
    @Operation(summary = "Delete the publisher")
    public ResponseEntity<HttpStatus> deletePublisher(@PathVariable("publisher_id") Long id) {
        publisherServiceImpl.delete(id);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}
