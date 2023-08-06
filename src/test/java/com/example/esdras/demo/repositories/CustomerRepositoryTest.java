package com.example.esdras.demo.repositories;

import com.example.esdras.demo.entities.CustomerEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

//USAMOS O TESTE DO JPA
@DataJpaTest
class CustomerRepositoryTest {

    //PEGAR O REPOSITORIO NO CONTEXTO DO SPRING
    @Autowired
    CustomerRepository customerRepository;

    @Test
    void saveCustomer() {
        CustomerEntity customerAdding = customerRepository.save(CustomerEntity.builder().name("Teste Cliente").build());
        assertThat(customerAdding.getName()).isEqualTo("Teste Cliente");
        assertThat(customerAdding.getId()).isNotNull();
    }

}