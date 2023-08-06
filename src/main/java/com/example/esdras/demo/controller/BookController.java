package com.example.esdras.demo.controller;

import com.example.esdras.demo.exceptions.NotFoundException;
import com.example.esdras.demo.dto.BookDto;
import com.example.esdras.demo.services.interfaces.BookService;
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



    @GetMapping(BOOK_PATH)
    public List<BookDto> listBooks(){
        return this.bookService.listBooks();
    }

    @GetMapping(BOOK_PATH_ID)
    public BookDto getBookById(@PathVariable("bookId") UUID id){
        return this.bookService.getBookById(id).orElseThrow(NotFoundException::new);
    }


    @DeleteMapping(BOOK_PATH_ID)
    public ResponseEntity deleteBookById(@PathVariable("bookId") UUID id){
        BookDto bookDeleted = this.bookService.deleteBookById(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(BOOK_PATH)
    public ResponseEntity saveNewBook(@RequestBody BookDto book){

        BookDto savedBook = this.bookService.saveNewBook(book);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", BOOK_PATH+'/' + savedBook.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }


    @PutMapping(BOOK_PATH_ID)
    public ResponseEntity updateBookById(@PathVariable("bookId") UUID id, @RequestBody BookDto book){
        BookDto bookPut= this.bookService.updateBookById(id,book);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(BOOK_PATH_ID)
    public ResponseEntity patchBookById(@PathVariable("bookId") UUID id, @RequestBody BookDto book){
        BookDto bookPatched= this.bookService.patchBookById(id,book);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



}
