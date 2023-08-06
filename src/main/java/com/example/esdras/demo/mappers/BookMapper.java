package com.example.esdras.demo.mappers;

import com.example.esdras.demo.dto.BookDto;
import com.example.esdras.demo.entities.BookEntity;
import org.mapstruct.Mapper;


//MAPPER VAI FUNCIONAR COMO O CONVERTER DE ENTITY PRO DTO E VICE VERSA
@Mapper
public interface BookMapper {
    BookEntity bookDtoToBookEntity(BookDto dto);

    BookDto bookEntityToBookDto(BookEntity entity);


}
