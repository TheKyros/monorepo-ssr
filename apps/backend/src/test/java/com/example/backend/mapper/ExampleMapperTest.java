package com.example.backend.mapper;

import com.example.backend.dto.CreateOrUpdateExampleRequest;
import com.example.backend.model.Example;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ExampleMapperTest {

    @Autowired
    private ExampleMapper exampleMapper;

    @Test
    void toEntity_ShouldMapAllFieldsCorrectly() {
        // Given
        CreateOrUpdateExampleRequest request = CreateOrUpdateExampleRequest.builder()
                .name("Test Name")
                .description("Test Description")
                .build();

        // When
        Example result = exampleMapper.toEntity(request);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNull(); // ID should be null as it's ignored
        assertThat(result.getName()).isEqualTo("Test Name");
        assertThat(result.getDescription()).isEqualTo("Test Description");
    }

    @Test
    void toEntity_WithNullValues_ShouldMapCorrectly() {
        // Given
        CreateOrUpdateExampleRequest request = CreateOrUpdateExampleRequest.builder()
                .name(null)
                .description(null)
                .build();

        // When
        Example result = exampleMapper.toEntity(request);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNull();
        assertThat(result.getName()).isNull();
        assertThat(result.getDescription()).isNull();
    }

    @Test
    void toEntity_WithNullRequest_ShouldMapCorrectly() {
        // Given
        CreateOrUpdateExampleRequest request = null;

        // When
        Example result = exampleMapper.toEntity(request);

        // Then
        assertThat(result).isNull();
    }
}
