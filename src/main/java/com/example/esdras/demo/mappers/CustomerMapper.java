package com.example.esdras.demo.mappers;

import com.example.esdras.demo.dto.BookDto;
import com.example.esdras.demo.entities.BookEntity;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    BookDto bookEntityToBookDto(BookEntity entity);
    BookEntity bookDtoToBookEntity(BookDto dto);
}

