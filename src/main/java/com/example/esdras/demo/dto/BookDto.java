package com.example.esdras.demo.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    //COLOCAMOS OS VALIDATORS IGUAL O CLASS VALIDATOR DO NEST
    @NotBlank
    @NotNull
    private String nameBook;
    @NotNull
    @NotBlank
    private String descriptionName;
    @NotNull
    private BigDecimal price;
}
