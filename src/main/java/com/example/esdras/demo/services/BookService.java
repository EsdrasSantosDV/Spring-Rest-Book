package com.example.esdras.demo.services;


import com.example.esdras.demo.model.Book;

import java.util.List;
import java.util.UUID;

public interface BookService
{

    List<Book> listBooks();


    Book getBookById(UUID id);

    Book saveNewBook(Book book);

    Book updateBookById(UUID id, Book book);

    Book deleteBookById(UUID id);

    Book patchBookById(UUID id, Book book);



}
