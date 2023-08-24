package com.example.esdras.demo.controller;

import com.example.esdras.demo.dto.BookDto;

import com.example.esdras.demo.services.mock.BookServiceImpl;
import com.example.esdras.demo.services.interfaces.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.core.Is.is;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

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

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<BookDto> bookArgumentCaptor;

    @BeforeEach
    void setUp() {
        bookServiceImpl=new BookServiceImpl();
    }

    @Test
    void patchBook()throws Exception{
        BookDto book=bookServiceImpl.listBooks().get(0);
        Map<String,Object> bookMap=new HashMap<>();
        bookMap.put("nameBook","New Title");
        mockMvc.perform(patch(BookController.BOOK_PATH_ID,book.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookMap)))
                .andExpect(status().isNoContent());

        verify(bookService).patchBookById(uuidArgumentCaptor.capture(),bookArgumentCaptor.capture());

        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(book.getId());

        assertThat(bookArgumentCaptor.getValue().getNameBook()).isEqualTo(bookMap.get("nameBook"));

    }

    @Test
    void deleteBook()throws Exception{
        BookDto book=bookServiceImpl.listBooks().get(0);
        given(bookService.deleteBookById(any())).willReturn(true);

        mockMvc.perform(delete(BookController.BOOK_PATH_ID,book.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        //ESTAMOS VERIFICANDO SE ESSE METODO FOI CHAMADO COM O ARGUMENTO QUE PASSAMOS
        verify(bookService).deleteBookById(uuidArgumentCaptor.capture());
        //AI CAPTURAMOS O ARGUMENTO QUE FOI PASSADO PRA ESSE METODO E VERIFICAMOS SE É O MESMO QUE PASSAMOS
        assertThat(book.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void updateBook() throws Exception {
        BookDto book=bookServiceImpl.listBooks().get(0);

        given(bookService.updateBookById(any(), any())).willReturn(Optional.of(book));
        mockMvc.perform(put(BookController.BOOK_PATH+"/"+book.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isNoContent());
        verify(bookService).updateBookById(any(UUID.class), any(BookDto.class));

    }



    @Test
    void createBook() throws Exception {
        BookDto testBook=bookServiceImpl.listBooks().get(0);
        testBook.setVersion(null);
        testBook.setId(null);


        given(bookService.saveNewBook(any(BookDto.class))).willReturn(bookServiceImpl.listBooks().get(1));
        mockMvc.perform(post(BookController.BOOK_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testBook)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

    }


    @Test
    void listAllBooks() throws Exception{
        given(bookService.listBooks()).willReturn(bookServiceImpl.listBooks());

        mockMvc.perform(get(BookController.BOOK_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(3)))
                .andExpect(jsonPath("$[0].id", is(bookServiceImpl.listBooks().get(0).getId().toString())))
                .andExpect(jsonPath("$[0].nameBook", is(bookServiceImpl.listBooks().get(0).getNameBook())));
    }

    //VAMOS LANÇAR UMA EXCEÇÃO COM O MOCKITO
    @Test
    void getBookByIdNotFound() throws Exception{
        given(bookService.getBookById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(BookController.BOOK_PATH+'/'+UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    void getBookById() throws Exception {
        BookDto testBook=bookServiceImpl.listBooks().get(0);
        given(bookService.getBookById(testBook.getId())).willReturn(Optional.of(testBook));

        //ESSAS IMPORTACOES ESTATICAS SÃO UMA MERDA
        //SEMPRE COPIA ELAS DAQUI
        //TO TESTNBADO PRO PRIMEIRO LIVRO QUE TA NO SET, AGORA TO ESPERANDO QUE SEJA UM JSON
        mockMvc.perform(get(BookController.BOOK_PATH+'/' +  testBook.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBook.getId().toString())))
                .andExpect(jsonPath("$.nameBook", is(testBook.getNameBook())));
        //aqui e o que eseperamos

    }
}