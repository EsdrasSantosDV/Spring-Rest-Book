package com.example.esdras.demo.controller;

import com.example.esdras.demo.model.Book;
import com.example.esdras.demo.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@AllArgsConstructor
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
    @GetMapping("/{beerId}")
    public Book getBookById(@PathVariable("beerId") UUID id){
        return this.bookService.getBookById(id);
    }

    //RequestMapping(value = "{beerId}",method = RequestMethod.DELETE)
    @DeleteMapping({"/{beerId}"})
    public Book deleteBookById(@PathVariable("beerId") UUID id){
        return this.bookService.deleteBookById(id);
    }

    @PostMapping
    public Book saveNewBook(@RequestBody Book book){
        return this.bookService.saveNewBook(book);
    }


    @PutMapping({"/{beerId}"})
    public Book updateBookById(@PathVariable("beerId") UUID id, @RequestBody Book book){
        return this.bookService.updateBookById(id,book);
    }

    @PatchMapping({"/{beerId}"})
    public Book patchBookById(@PathVariable("beerId") UUID id, @RequestBody Book book){
       return this.bookService.patchBookById(id,book);
    }



}
