package com.example.backend.service;

import com.example.backend.dto.CreateOrUpdateExampleRequest;
import com.example.backend.mapper.ExampleMapper;
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
    private final ExampleMapper exampleMapper;

    @Transactional(readOnly = true)
    public List<Example> findAll() {
        return exampleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Example> findById(Long id) {
        return exampleRepository.findById(id);
    }

    @Transactional
    public Example create(CreateOrUpdateExampleRequest request) {
        Example example = exampleMapper.toEntity(request);
        return exampleRepository.save(example);
    }

    @Transactional
    public Example update(Long id, CreateOrUpdateExampleRequest request) {
        Example example = exampleMapper.toEntity(request);
        example.setId(id);
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
