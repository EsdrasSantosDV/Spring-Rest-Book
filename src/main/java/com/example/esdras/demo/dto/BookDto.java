package com.example.esdras.demo.dto;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@Getter
public class BookDto {

    private UUID id;
    private Integer version;
    private String nameBook;
    private String descriptionName;
    private BigDecimal price;
}
