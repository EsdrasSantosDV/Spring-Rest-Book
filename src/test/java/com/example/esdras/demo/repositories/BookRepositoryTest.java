package com.example.esdras.demo.repositories;

import com.example.esdras.demo.entities.BookEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    void saveBook() {
        BookEntity bookAdding = bookRepository.save(BookEntity.builder().nameBook("Teste Livro").descriptionName("Livro Teste").build());
        assertThat(bookAdding.getNameBook()).isEqualTo("Teste Livro");
        assertThat(bookAdding.getId()).isNotNull();
    }
}