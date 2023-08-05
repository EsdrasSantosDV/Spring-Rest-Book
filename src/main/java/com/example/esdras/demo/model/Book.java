package com.example.esdras.demo.model;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@Getter
public class Book {

    private UUID id;
    private Integer version;
    private String nameBook;
    private String descriptionName;
    private BigDecimal price;
}
