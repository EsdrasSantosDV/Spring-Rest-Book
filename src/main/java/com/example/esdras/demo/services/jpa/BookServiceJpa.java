package com.example.esdras.demo.services.jpa;

import com.example.esdras.demo.dto.BookDto;
import com.example.esdras.demo.mappers.BookMapper;
import com.example.esdras.demo.repositories.BookRepository;
import com.example.esdras.demo.services.interfaces.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


//USAR O PRIMARY PRA SER ESSA DE PREFERENCIA
@Service
@RequiredArgsConstructor
@Primary
public class BookServiceJpa implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public List<BookDto> listBooks() {
        return bookRepository.findAll().stream().map(bookMapper::bookEntityToBookDto).collect(Collectors.toList());
    }

    @Override
    public Optional<BookDto> getBookById(UUID id) {
        return Optional.ofNullable(bookMapper.bookEntityToBookDto(bookRepository.findById(id).orElse(null)));
    }

    @Override
    public BookDto saveNewBook(BookDto book) {
        return null;
    }

    @Override
    public BookDto updateBookById(UUID id, BookDto book) {
        return null;
    }

    @Override
    public BookDto deleteBookById(UUID id) {
        return null;
    }

    @Override
    public BookDto patchBookById(UUID id, BookDto book) {
        return null;
    }
}
