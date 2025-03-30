package com.example.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateOrUpdateExampleRequest(
        @NotBlank(message = "Name is required") String name,
        String description) {
}