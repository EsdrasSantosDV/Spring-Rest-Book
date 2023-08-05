package com.example.esdras.demo.controller;

import com.example.esdras.demo.model.Book;
import com.example.esdras.demo.services.interfaces.BookService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
//AO INVES DE MAPEARMOS CADA COM O REQUEST MAPPING PODEMOS OCLOCAR ASSIM
@RequestMapping("/api/v1/book")
public class BookController {
    private final BookService bookService;


    //@RequestMapping(method = RequestMethod.GET)
    @GetMapping
    public List<Book> listBooks(){
        return this.bookService.listBooks();
    }

    //@RequestMapping(value = "{beerId}",method = RequestMethod.GET)
    @GetMapping("/{bookId}")
    public Book getBookById(@PathVariable("bookId") UUID id){
        return this.bookService.getBookById(id);
    }

    //RequestMapping(value = "{beerId}",method = RequestMethod.DELETE)
    @DeleteMapping({"/{bookId}"})
    public Book deleteBookById(@PathVariable("bookId") UUID id){
        return this.bookService.deleteBookById(id);
    }

    @PostMapping
    public Book saveNewBook(@RequestBody Book book){
        return this.bookService.saveNewBook(book);
    }


    @PutMapping({"/{bookId}"})
    public Book updateBookById(@PathVariable("bookId") UUID id, @RequestBody Book book){
        return this.bookService.updateBookById(id,book);
    }

    @PatchMapping({"/{bookId}"})
    public Book patchBookById(@PathVariable("bookId") UUID id, @RequestBody Book book){
       return this.bookService.patchBookById(id,book);
    }



}
