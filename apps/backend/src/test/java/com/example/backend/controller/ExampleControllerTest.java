package com.example.backend.controller;

import com.example.backend.dto.CreateExampleRequest;
import com.example.backend.model.Example;
import com.example.backend.service.ExampleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExampleController.class)
class ExampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
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
    void getAllExamples_ShouldReturnListOfExamples() throws Exception {
        when(exampleService.findAll()).thenReturn(Arrays.asList(testExample));

        mockMvc.perform(get("/examples"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Example"))
                .andExpect(jsonPath("$[0].description").value("Test Description"));
    }

    @Test
    void getExampleById_WhenExampleExists_ShouldReturnExample() throws Exception {
        when(exampleService.findById(1L)).thenReturn(Optional.of(testExample));

        mockMvc.perform(get("/examples/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Example"))
                .andExpect(jsonPath("$.description").value("Test Description"));
    }

    @Test
    void getExampleById_WhenExampleDoesNotExist_ShouldReturn404() throws Exception {
        when(exampleService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/examples/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createExample_ShouldReturnCreatedExample() throws Exception {
        when(exampleService.create(any(CreateExampleRequest.class))).thenReturn(testExample);

        mockMvc.perform(post("/examples")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"New Example\",\"description\":\"New Description\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Example"))
                .andExpect(jsonPath("$.description").value("Test Description"));
    }

    @Test
    void updateExample_WhenExampleExists_ShouldReturnUpdatedExample() throws Exception {
        when(exampleService.findById(1L)).thenReturn(Optional.of(testExample));
        when(exampleService.save(any(Example.class))).thenReturn(testExample);

        mockMvc.perform(put("/examples/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated Example\",\"description\":\"Updated Description\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Example"))
                .andExpect(jsonPath("$.description").value("Test Description"));
    }

    @Test
    void updateExample_WhenExampleDoesNotExist_ShouldReturn404() throws Exception {
        when(exampleService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/examples/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated Example\",\"description\":\"Updated Description\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteExample_WhenExampleExists_ShouldReturn200() throws Exception {
        when(exampleService.findById(1L)).thenReturn(Optional.of(testExample));

        mockMvc.perform(delete("/examples/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteExample_WhenExampleDoesNotExist_ShouldReturn404() throws Exception {
        when(exampleService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/examples/1"))
                .andExpect(status().isNotFound());
    }
}
