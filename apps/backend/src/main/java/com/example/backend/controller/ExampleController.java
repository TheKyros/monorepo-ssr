package com.example.backend.controller;

import com.example.backend.dto.CreateOrUpdateExampleRequest;
import com.example.backend.model.Example;
import com.example.backend.service.ExampleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/examples")
@RequiredArgsConstructor
@Tag(name = "Example", description = "Example management APIs")
public class ExampleController {
    private final ExampleService exampleService;

    @Operation(summary = "Get all examples", description = "Retrieves a list of all examples")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved examples")
    })
    @GetMapping
    public ResponseEntity<List<Example>> getAllExamples() {
        return ResponseEntity.ok(exampleService.findAll());
    }

    @Operation(summary = "Get example by ID", description = "Retrieves a specific example by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the example"),
            @ApiResponse(responseCode = "404", description = "Example not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Example> getExampleById(
            @Parameter(description = "ID of the example to retrieve") @PathVariable Long id) {
        return exampleService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create new example", description = "Creates a new example")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the example")
    })
    @PostMapping
    public ResponseEntity<Example> createExample(
            @Parameter(description = "Example data to create") @Valid @RequestBody CreateOrUpdateExampleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(exampleService.create(request));
    }

    @Operation(summary = "Update example", description = "Updates an existing example")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the example"),
            @ApiResponse(responseCode = "404", description = "Example not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Example> updateExample(
            @Parameter(description = "ID of the example to update") @PathVariable Long id,
            @Parameter(description = "Updated example object") @Valid @RequestBody CreateOrUpdateExampleRequest request) {
        return exampleService.findById(id)
                .map(existingExample -> {
                    return ResponseEntity.ok(exampleService.update(id, request));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete example", description = "Deletes an existing example")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the example"),
            @ApiResponse(responseCode = "404", description = "Example not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExample(
            @Parameter(description = "ID of the example to delete") @PathVariable Long id) {
        return exampleService.findById(id)
                .map(example -> {
                    exampleService.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
