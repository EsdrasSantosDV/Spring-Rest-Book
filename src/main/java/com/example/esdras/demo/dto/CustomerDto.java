package com.example.esdras.demo.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class CustomerDto {
    private UUID id;
    private String name;
    private Integer version;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
