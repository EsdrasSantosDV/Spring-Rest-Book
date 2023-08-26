package com.example.esdras.demo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.UUID;

//NA ENTITY TAMBEM COLOCAMOS OS VALIDADORES
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BookEntity {
    //COMO ESTAMOS USANDO UUI, USAMOS ESSA ESTRATEGIA DE GERAÇÃO
    //COLOCANDO 36 DE TAMANHO, SENDO VARCHAR, E FAZENDO COM QUE NÃO POSSAR SER ALTERADO NEM NULO
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID id;
    @Version
    private Integer version;

    //COLOCAMOS NA COLUNA O TAMANHO MAXIMO DE 50 CARACTERES
    //O SIZE E PRA ANTES DE BATER NO BANCO
    @NotNull
    @NotBlank
    @Size(max = 50)
    @Column(length = 50)
    private String nameBook;

    @NotNull
    @NotBlank
    @Size(max = 255)
    private String descriptionName;

    @NotNull
    private BigDecimal price;

}
