package com.example.esdras.demo.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.UUID;
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
    private String nameBook;
    private String descriptionName;
    private BigDecimal price;
}
