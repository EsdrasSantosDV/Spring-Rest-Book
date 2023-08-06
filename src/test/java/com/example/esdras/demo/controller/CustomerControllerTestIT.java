package com.example.esdras.demo.controller;

import com.example.esdras.demo.dto.CustomerDto;
import com.example.esdras.demo.entities.CustomerEntity;
import com.example.esdras.demo.exceptions.NotFoundException;
import com.example.esdras.demo.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


//ESSE E UM TESTE DE INTEGRAÇÃO QUE IREMOS REALIZAR
//VAMOS TRAZER TODO O CONTEXTO DO SPRING PARA O TESTE
@SpringBootTest
class CustomerControllerTestIT {


    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;



    @Rollback
    @Transactional
    @Test
    void createCustomer() {

        CustomerDto dto=CustomerDto.builder().name("Teste").build();

        ResponseEntity responseEntity= customerController.createCustomer(dto);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();
        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        CustomerEntity customer=customerRepository.findById(savedUUID).get();
        assertThat(customer.getName()).isEqualTo(dto.getName());
        assertThat(customer.getId()).isEqualTo(savedUUID);
    }

    @Test
    void listAllCustomers() {

        List<CustomerDto> dtos= customerController.listAllCustomers();

        assertEquals(3,dtos.size());
        assertThat(dtos.size()).isEqualTo(3);

    }

    //MAS QUANDO QUISERMOS REALIZAR UM TESTE QUE EXIGE PERSISTENCIA E ALTERAÇÃO DOS DADOS
    //FAZEMNOS ASSIM E COM ISSO NÃO AFETA OS OUTROS TESTES
    @Rollback
    @Transactional
    @Test
    void deleteAll()
    {
        customerRepository.deleteAll();
        List<CustomerDto> dtos= customerController.listAllCustomers();

        assertEquals(0,dtos.size());
        assertThat(dtos.size()).isEqualTo(0);
    }


    @Test
    void getCustomerByIdNotFound()
    {
        //usamos o assert throwss pra ver se lança uma exceção especifica

        assertThrows(NotFoundException.class, () -> {
            customerController.getCustomerById(UUID.randomUUID());
        });

    }


    @Test
    void getCustomerById() {
        CustomerEntity customer= customerRepository.findAll().get(0);


        CustomerDto dto2= customerController.getCustomerById(customer.getId());

        assertThat(dto2.getId()).isEqualTo(customer.getId());

        assertThat(dto2.getId()).isNotNull();



    }
}