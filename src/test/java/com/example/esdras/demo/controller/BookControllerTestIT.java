package com.example.esdras.demo.controller;

import com.example.esdras.demo.dto.BookDto;
import com.example.esdras.demo.dto.CustomerDto;
import com.example.esdras.demo.entities.BookEntity;
import com.example.esdras.demo.exceptions.NotFoundException;
import com.example.esdras.demo.mappers.BookMapper;
import com.example.esdras.demo.repositories.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.awt.print.Book;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
class BookControllerTestIT {

    @Autowired
    BookController bookController;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookMapper bookMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;



    //BOTAR O CONTEXTO DO SPRING
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void testPatchBeerBadName() throws Exception {
        BookEntity book = bookRepository.findAll().get(0);

        Map<String, Object> bookMap = new HashMap<>();
        bookMap.put("nameBook", "New Name 1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");

        mockMvc.perform(patch(BookController.BOOK_PATH_ID, book.getId()).
                contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(bookMap))).andExpect(status().isBadRequest());

    }
        @Test
    void testDeleteByIDNotFound() {
        assertThrows(NotFoundException.class, () -> {
            bookController.deleteBookById(UUID.randomUUID());
        });
    }

    @Rollback
    @Transactional
    @Test
    void deleteByIdFound() {
        BookEntity book = bookRepository.findAll().get(0);

        ResponseEntity responseEntity = bookController.deleteBookById(book.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        assertThat(bookRepository.findById(book.getId()).isEmpty());
    }


    @Test
    void listAllBooks() {
        List<BookDto> dtos= bookController.listBooks();

        assertThat(dtos.size()).isEqualTo(3);
        assertNotNull(dtos);
    }


    @Test
    void testUpdateNotFound() {
        assertThrows(NotFoundException.class, () -> {
            bookController.updateBookById(UUID.randomUUID(), BookDto.builder().build());
        });
    }


    @Rollback
    @Transactional
    @Test
    void updateExistingBeer() {
        BookEntity beer = bookRepository.findAll().get(0);
        BookDto bookDto = bookMapper.bookEntityToBookDto(beer);
        bookDto.setId(null);
        bookDto.setVersion(null);
        final String beerName = "UPDATED";
        bookDto.setNameBook(beerName);

        ResponseEntity responseEntity = bookController.updateBookById(beer.getId(), bookDto);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        BookEntity updatedBeer = bookRepository.findById(beer.getId()).get();
        assertThat(updatedBeer.getNameBook()).isEqualTo(beerName);
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