package com.example.esdras.demo.bootstrap;

import com.example.esdras.demo.repositories.BookRepository;
import com.example.esdras.demo.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BootstrapDataTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BookRepository bookRepository;


    BootstrapData bootstrapData;

    @BeforeEach
    void setUp() {
        bootstrapData = new BootstrapData(customerRepository, bookRepository);
    }

    @Test
    void Testrun() throws Exception {
        bootstrapData.run(null);

        assertThat(bookRepository.count()).isEqualTo(3);
        assertThat(customerRepository.count()).isEqualTo(3);
    }

}