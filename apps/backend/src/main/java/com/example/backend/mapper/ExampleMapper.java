package com.example.backend.mapper;

import com.example.backend.dto.CreateOrUpdateExampleRequest;
import com.example.backend.model.Example;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExampleMapper {

    @Mapping(target = "id", ignore = true)
    Example toEntity(CreateOrUpdateExampleRequest request);
}
