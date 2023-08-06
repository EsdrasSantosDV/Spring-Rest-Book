package com.example.esdras.demo.mappers;

import com.example.esdras.demo.dto.BookDto;
import com.example.esdras.demo.dto.CustomerDto;
import com.example.esdras.demo.entities.BookEntity;
import com.example.esdras.demo.entities.CustomerEntity;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    CustomerDto customerEntityToCustomerDto(CustomerEntity entity);
    CustomerEntity customerDtoToCustomerEntity(CustomerDto dto);
}

