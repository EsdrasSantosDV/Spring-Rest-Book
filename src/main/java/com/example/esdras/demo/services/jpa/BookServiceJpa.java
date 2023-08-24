package com.example.esdras.demo.services.jpa;

import com.example.esdras.demo.dto.BookDto;
import com.example.esdras.demo.mappers.BookMapper;
import com.example.esdras.demo.repositories.BookRepository;
import com.example.esdras.demo.services.interfaces.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
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
        return bookMapper.bookEntityToBookDto(bookRepository.save(bookMapper.bookDtoToBookEntity(book)));
    }

    @Override
    public Optional<BookDto> updateBookById(UUID id, BookDto book) {
        AtomicReference<Optional<BookDto>> atomicReference = new AtomicReference<>();

        bookRepository.findById(id).ifPresentOrElse(foundBook -> {
            foundBook.setNameBook(book.getNameBook());
            foundBook.setPrice(book.getPrice());
            foundBook.setDescriptionName(book.getDescriptionName());
            atomicReference.set(Optional.of(bookMapper
                    .bookEntityToBookDto(bookRepository.save(foundBook))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public Boolean deleteBookById(UUID id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<BookDto> patchBookById(UUID id, BookDto book) {
        AtomicReference<Optional<BookDto>> atomicReference = new AtomicReference<>();

        bookRepository.findById(id).ifPresentOrElse(foundBook -> {
            if (StringUtils.hasText(book.getNameBook())){
                foundBook.setNameBook(book.getNameBook());
            }

            if(StringUtils.hasText(book.getDescriptionName())){
                foundBook.setDescriptionName(book.getDescriptionName());
            }

            if(book.getPrice()!=null){
                foundBook.setPrice(book.getPrice());
            }

            atomicReference.set(Optional.of(bookMapper
                    .bookEntityToBookDto(bookRepository.save(foundBook))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }
}
