package com.example.backend.service;

import com.example.backend.dto.CreateExampleRequest;
import com.example.backend.model.Example;
import com.example.backend.repository.ExampleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

    @InjectMocks
    private ExampleService exampleService;

    private Example testExample;

    @BeforeEach
    void setUp() {
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
        CreateExampleRequest request = new CreateExampleRequest("New Example", "New Description");

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