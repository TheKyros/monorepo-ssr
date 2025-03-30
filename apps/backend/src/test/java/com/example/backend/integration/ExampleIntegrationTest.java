package com.example.backend.integration;

import com.example.backend.dto.CreateOrUpdateExampleRequest;
import com.example.backend.model.Example;
import com.example.backend.repository.ExampleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ExampleIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ExampleRepository exampleRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        exampleRepository.deleteAll();
    }

    @Test
    void createAndRetrieveExample_ShouldWork() throws Exception {
        // Create example
        CreateOrUpdateExampleRequest request = CreateOrUpdateExampleRequest.builder()
                .name("Integration Test Example")
                .description("Integration Test Description")
                .build();

        String response = mockMvc.perform(post("/examples")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Integration Test Example"))
                .andExpect(jsonPath("$.description").value("Integration Test Description"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Example createdExample = objectMapper.readValue(response, Example.class);

        // Verify it was saved in the database
        assertThat(exampleRepository.findById(createdExample.getId())).isPresent();

        // Retrieve it
        mockMvc.perform(get("/examples/" + createdExample.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Integration Test Example"))
                .andExpect(jsonPath("$.description").value("Integration Test Description"));
    }

    @Test
    void updateExample_ShouldWork() throws Exception {
        // Create initial example
        CreateOrUpdateExampleRequest createRequest = CreateOrUpdateExampleRequest.builder()
                .name("Initial Name")
                .description("Initial Description")
                .build();

        String response = mockMvc.perform(post("/examples")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Example createdExample = objectMapper.readValue(response, Example.class);

        // Update example
        createdExample.setName("Updated Name");
        createdExample.setDescription("Updated Description");

        mockMvc.perform(put("/examples/" + createdExample.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createdExample)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.description").value("Updated Description"));

        // Verify update in database
        Example updatedExample = exampleRepository.findById(createdExample.getId()).orElseThrow();
        assertThat(updatedExample.getName()).isEqualTo("Updated Name");
        assertThat(updatedExample.getDescription()).isEqualTo("Updated Description");
    }

    @Test
    void deleteExample_ShouldWork() throws Exception {
        // Create example
        CreateOrUpdateExampleRequest request = new CreateOrUpdateExampleRequest("To Delete", "Will be deleted");

        String response = mockMvc.perform(post("/examples")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Example createdExample = objectMapper.readValue(response, Example.class);

        // Delete example
        mockMvc.perform(delete("/examples/" + createdExample.getId()))
                .andExpect(status().isNoContent());

        // Verify deletion
        assertThat(exampleRepository.findById(createdExample.getId())).isEmpty();
    }

    @Test
    void getAllExamples_ShouldWork() throws Exception {
        // Create multiple examples
        CreateOrUpdateExampleRequest request1 = new CreateOrUpdateExampleRequest("First Example",
                "First Description");
        CreateOrUpdateExampleRequest request2 = new CreateOrUpdateExampleRequest("Second Example",
                "Second Description");

        mockMvc.perform(post("/examples")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request1)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/examples")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request2)))
                .andExpect(status().isCreated());

        // Retrieve all examples
        mockMvc.perform(get("/examples"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("First Example"))
                .andExpect(jsonPath("$[1].name").value("Second Example"));
    }
}
