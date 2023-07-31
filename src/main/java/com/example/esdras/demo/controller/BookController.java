package com.example.esdras.demo.controller;

import com.example.esdras.demo.model.Book;
import com.example.esdras.demo.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@RestController
public class BookController {
    private final BookService bookService;


    @RequestMapping(method = RequestMethod.GET)
    public List<Book> listBooks(){
        return this.bookService.listBooks();
    }

    @RequestMapping(value = "{beerId}", method = RequestMethod.GET)
    public Book getBookById(@PathVariable("beerId") UUID id){
        return this.bookService.getBookById(id);
    }

    @RequestMapping(value = "{beerId}",method = RequestMethod.DELETE)
    public Book deleteBookById(@PathVariable("beerId") UUID id){
        return this.bookService.deleteBookById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Book saveNewBook(@RequestBody Book book){
        return this.bookService.saveNewBook(book);
    }


    @RequestMapping(method = RequestMethod.PUT)
    public Book updateBookById(@PathVariable("beerId") UUID id, @RequestBody Book book){
        return this.bookService.updateBookById(id,book);
    }

    @RequestMapping(method=RequestMethod.PATCH)
    public Book patchBookById(@PathVariable("beerId") UUID id, @RequestBody Book book){
       return this.bookService.patchBookById(id,book);
    }



}
