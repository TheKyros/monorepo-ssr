package com.example.backend.service;

import com.example.backend.dto.CreateExampleRequest;
import com.example.backend.model.Example;
import com.example.backend.repository.ExampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExampleService {
    private final ExampleRepository exampleRepository;

    @Transactional(readOnly = true)
    public List<Example> findAll() {
        return exampleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Example> findById(Long id) {
        return exampleRepository.findById(id);
    }

    @Transactional
    public Example create(CreateExampleRequest request) {
        Example example = new Example();
        example.setName(request.name());
        example.setDescription(request.description());
        return exampleRepository.save(example);
    }

    @Transactional
    public Example save(Example example) {
        return exampleRepository.save(example);
    }

    @Transactional
    public void deleteById(Long id) {
        exampleRepository.deleteById(id);
    }
}