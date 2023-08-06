package com.example.esdras.demo.services.interfaces;


import com.example.esdras.demo.model.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookService
{

    List<Book> listBooks();

    Optional<Book> getBookById(UUID id);

    Book saveNewBook(Book book);

    Book updateBookById(UUID id, Book book);

    Book deleteBookById(UUID id);

    Book patchBookById(UUID id, Book book);


}
