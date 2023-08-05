package com.example.esdras.demo.controller;

import com.example.esdras.demo.services.interfaces.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

//TESTAMOS SO A CLASSE BOOK CONTROLLER
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    MockMvc mockMvc;

    //PRA USARMOS A DEPEDNECIA DO BOOKSERVICE SENÃO O SPRING RECLAMA
    @MockBean
    BookService bookService;
    @Test
    void getBookById() throws Exception {
        mockMvc.perform(get("/api/v1/book/" + UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}