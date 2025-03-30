package com.example.backend.service;

import com.example.backend.dto.CreateOrUpdateExampleRequest;
import com.example.backend.mapper.ExampleMapper;
import com.example.backend.model.Example;
import com.example.backend.repository.ExampleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExampleServiceTest {

    @Mock
    private ExampleRepository exampleRepository;

    private ExampleMapper exampleMapper;

    private ExampleService exampleService;

    private Example testExample;

    @BeforeEach
    void setUp() {
        exampleMapper = Mappers.getMapper(ExampleMapper.class);
        exampleService = new ExampleService(exampleRepository, exampleMapper);
        testExample = new Example();
        testExample.setId(1L);
        testExample.setName("Test Example");
        testExample.setDescription("Test Description");
    }

    @Test
    void findAll_ShouldReturnAllExamples() {
        when(exampleRepository.findAll()).thenReturn(Arrays.asList(testExample));

        var result = exampleService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(testExample);
        verify(exampleRepository).findAll();
    }

    @Test
    void findById_WhenExampleExists_ShouldReturnExample() {
        when(exampleRepository.findById(1L)).thenReturn(Optional.of(testExample));

        var result = exampleService.findById(1L);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(testExample);
        verify(exampleRepository).findById(1L);
    }

    @Test
    void findById_WhenExampleDoesNotExist_ShouldReturnEmpty() {
        when(exampleRepository.findById(1L)).thenReturn(Optional.empty());

        var result = exampleService.findById(1L);

        assertThat(result).isEmpty();
        verify(exampleRepository).findById(1L);
    }

    @Test
    void create_ShouldCreateAndReturnExample() {
        CreateOrUpdateExampleRequest request = new CreateOrUpdateExampleRequest("New Example", "New Description");

        when(exampleRepository.save(any(Example.class))).thenReturn(testExample);

        var result = exampleService.create(request);

        assertThat(result).isEqualTo(testExample);
        verify(exampleRepository).save(any(Example.class));
    }

    @Test
    void save_ShouldSaveAndReturnExample() {
        when(exampleRepository.save(any(Example.class))).thenReturn(testExample);

        var result = exampleService.save(testExample);

        assertThat(result).isEqualTo(testExample);
        verify(exampleRepository).save(testExample);
    }

    @Test
    void deleteById_ShouldDeleteExample() {
        exampleService.deleteById(1L);

        verify(exampleRepository).deleteById(1L);
    }
}
