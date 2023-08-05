package com.example.esdras.demo.controller;

import com.example.esdras.demo.model.Book;

import com.example.esdras.demo.services.BookServiceImpl;
import com.example.esdras.demo.services.interfaces.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.core.Is.is;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

//TESTAMOS SO A CLASSE BOOK CONTROLLER
@WebMvcTest(BookController.class)
class BookControllerTest {

    //PRECISAMOS DO MOCK MVC PARA FAZER REQUISIÇÕES HTTP
    @Autowired
    MockMvc mockMvc;

    //PRA USARMOS A DEPEDNECIA DO BOOKSERVICE SENÃO O SPRING RECLAMA
    @MockBean
    BookService bookService;

    //PRECISAMOS DO MAPPER DO SPRING BOOT, OND EVAMOS TRANSFORMAR UM POJO NUM JSON
    @Autowired
    ObjectMapper objectMapper;

    BookServiceImpl bookServiceImpl;

    @BeforeEach
    void setUp() {
        bookServiceImpl=new BookServiceImpl();
    }
    @Test
    void createBook() throws Exception {
        Book testBook=bookServiceImpl.listBooks().get(0);
        testBook.setVersion(null);
        testBook.setId(null);


        given(bookService.saveNewBook(any(Book.class))).willReturn(bookServiceImpl.listBooks().get(1));
        mockMvc.perform(post("/api/v1/book")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testBook)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

    }


    @Test
    void listAllBooks() throws Exception{
        //
        given(bookService.listBooks()).willReturn(bookServiceImpl.listBooks());

        mockMvc.perform(get("/api/v1/book")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(3)))
                .andExpect(jsonPath("$[0].id", is(bookServiceImpl.listBooks().get(0).getId().toString())))
                .andExpect(jsonPath("$[0].nameBook", is(bookServiceImpl.listBooks().get(0).getNameBook())));
    }


    @Test
    void getBookById() throws Exception {
        Book testBook=bookServiceImpl.listBooks().get(0);
        given(bookService.getBookById(testBook.getId())).willReturn(testBook);

        //ESSAS IMPORTACOES ESTATICAS SÃO UMA MERDA
        //SEMPRE COPIA ELAS DAQUI
        //TO TESTNBADO PRO PRIMEIRO LIVRO QUE TA NO SET, AGORA TO ESPERANDO QUE SEJA UM JSON
        mockMvc.perform(get("/api/v1/book/" +  testBook.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBook.getId().toString())))
                .andExpect(jsonPath("$.nameBook", is(testBook.getNameBook())));
        //aqui e o que eseperamos

    }
}