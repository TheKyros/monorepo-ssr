package com.example.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateExampleRequest(
        @NotBlank(message = "Name is required") String name,
        String description) {
}