package com.example.esdras.demo.bootstrap;

import com.example.esdras.demo.dto.BookDto;
import com.example.esdras.demo.dto.CustomerDto;
import com.example.esdras.demo.entities.BookEntity;
import com.example.esdras.demo.entities.CustomerEntity;
import com.example.esdras.demo.repositories.BookRepository;
import com.example.esdras.demo.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner
{

    //DEPENDENCIAS NECESSARIAS
    private final CustomerRepository customerRepository;
    private final BookRepository bookRepository;


    @Override
    public void run(String... args) throws Exception {
        populateBooksData();
        populateCustomersData();
    }

    private void populateBooksData()
    {
        if(bookRepository.count() == 0)
        {
            BookEntity book1 = BookEntity.builder().nameBook("Arquitetura").
                    descriptionName("MUITO BOM").price(new BigDecimal("100.00")).build();
            BookEntity book2 = BookEntity.builder().nameBook("Arquitetura 2").
                    descriptionName("MUITO BOM d").price(new BigDecimal("100.00")).build();
            BookEntity book3 = BookEntity.builder().nameBook("Arquitetura 3").
                    descriptionName("MUITO BOM D").price(new BigDecimal("100.00")).build();

            bookRepository.save(book1);
            bookRepository.save(book2);
            bookRepository.save(book3);

        }
    }

    private void populateCustomersData()
    {
        if(customerRepository.count()==0)
        {
            CustomerEntity customer1 = CustomerEntity.builder()
                    .name("Customer 1")
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            CustomerEntity customer2 = CustomerEntity.builder()
                    .name("Customer 2")
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            CustomerEntity customer3 = CustomerEntity.builder()
                    .name("Customer 3")
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            customerRepository.save(customer1);
            customerRepository.save(customer2);
            customerRepository.save(customer3);

        }
    }






}


