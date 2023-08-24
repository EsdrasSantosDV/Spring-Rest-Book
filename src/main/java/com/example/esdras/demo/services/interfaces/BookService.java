package com.example.esdras.demo.services.interfaces;


import com.example.esdras.demo.dto.BookDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookService
{

    List<BookDto> listBooks();

    Optional<BookDto> getBookById(UUID id);

    BookDto saveNewBook(BookDto book);

    Optional<BookDto> updateBookById(UUID id, BookDto book);

    Boolean deleteBookById(UUID id);

    Optional<BookDto> patchBookById(UUID id, BookDto book);


}
