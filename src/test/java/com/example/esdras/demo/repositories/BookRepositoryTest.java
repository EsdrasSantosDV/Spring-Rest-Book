package com.example.esdras.demo.repositories;

import com.example.esdras.demo.entities.BookEntity;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    void testSaveBookNameTooLong() {

        assertThrows(ConstraintViolationException.class, () -> {
            BookEntity savedBook = bookRepository.save(BookEntity.builder()
                    .nameBook("My book 0123345678901233456789012334567890123345678901233456789012334567890123345678901233456789")
                    .descriptionName("2342342asddsa34234")
                    .price(new BigDecimal("11.99"))
                    .build());

            bookRepository.flush();
        });
    }


    @Test
    void saveBook() {
        BookEntity bookAdding = bookRepository.save(BookEntity.builder().nameBook("Teste Livro").
                descriptionName("Livro Teste").price(new BigDecimal("11.92")).build());

        bookRepository.flush();
        assertThat(bookAdding.getNameBook()).isEqualTo("Teste Livro");
        assertThat(bookAdding.getId()).isNotNull();
    }
}