package com.example.esdras.demo.controller;

import com.example.esdras.demo.dto.BookDto;
import com.example.esdras.demo.dto.CustomerDto;
import com.example.esdras.demo.entities.BookEntity;
import com.example.esdras.demo.repositories.BookRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class BookControllerTestIT {

    @Autowired
    BookController bookController;

    @Autowired
    BookRepository bookRepository;


    @Test
    void listAllBooks() {
        List<BookDto> dtos= bookController.listBooks();

        assertThat(dtos.size()).isEqualTo(3);
        assertNotNull(dtos);
    }

    @Test
    void getBookByIdNotFound()
    {
        assertThrows(RuntimeException.class,()->bookController.getBookById(UUID.randomUUID()));
    }


    //PODEMOS USAR O DISPLAY NAME PRA DAR UM NOME AO TESTE
    @Test
    @DisplayName("Teste de GET ID do livro")
    void getBookByID()
    {
        BookEntity book= bookRepository.findAll().get(0);

        BookDto dto= bookController.getBookById(book.getId());

        assertThat(book.getId()).isEqualTo(dto.getId());


    }


    @Rollback
    @Transactional
    @Test
    void saveNewBook() {

        BookDto dto=BookDto.builder().nameBook("teste").build();


        ResponseEntity responseEntity = bookController.saveNewBook(dto);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        BookEntity book = bookRepository.findById(savedUUID).get();
        assertThat(book).isNotNull();
    }



    @Rollback
    @Transactional
    @Test
    void deleteAll()
    {
        bookRepository.deleteAll();
        List<BookDto> dtos= bookController.listBooks();

        assertEquals(0,dtos.size());
        assertThat(dtos.size()).isEqualTo(0);
    }









}