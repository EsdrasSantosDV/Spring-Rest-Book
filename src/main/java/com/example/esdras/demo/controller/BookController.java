package com.example.esdras.demo.controller;

import com.example.esdras.demo.model.Book;
import com.example.esdras.demo.services.interfaces.BookService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BookController {
    private final BookService bookService;

    public static final String BOOK_PATH = "/api/v1/book";
    public static final String BOOK_PATH_ID = BOOK_PATH + "/{bookId}";



    @GetMapping
    public List<Book> listBooks(){
        return this.bookService.listBooks();
    }

    @GetMapping(BOOK_PATH_ID)
    public Book getBookById(@PathVariable("bookId") UUID id){
        return this.bookService.getBookById(id);
    }


    @DeleteMapping(BOOK_PATH_ID)
    public Book deleteBookById(@PathVariable("bookId") UUID id){
        return this.bookService.deleteBookById(id);
    }

    @PostMapping
    public ResponseEntity saveNewBook(@RequestBody Book book){

        Book savedBook = this.bookService.saveNewBook(book);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", BOOK_PATH+'/' + savedBook.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }


    @PutMapping(BOOK_PATH_ID)
    public ResponseEntity updateBookById(@PathVariable("bookId") UUID id, @RequestBody Book book){
        Book bookPut= this.bookService.updateBookById(id,book);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(BOOK_PATH_ID)
    public Book patchBookById(@PathVariable("bookId") UUID id, @RequestBody Book book){
       return this.bookService.patchBookById(id,book);
    }



}
