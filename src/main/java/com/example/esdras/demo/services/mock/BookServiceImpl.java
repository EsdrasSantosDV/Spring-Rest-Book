package com.example.esdras.demo.services.mock;

import com.example.esdras.demo.dto.BookDto;
import com.example.esdras.demo.services.interfaces.BookService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

//COMO E INJETAVEL

@Service
public class BookServiceImpl implements BookService {

    private final Map<UUID, BookDto> bookMap;


    public BookServiceImpl(

                          ) {
        this.bookMap = new HashMap<>();

        BookDto book1 = BookDto.builder().id(UUID.randomUUID()).nameBook("Arquitetura").descriptionName("MUITO BOM").price(new BigDecimal("100.00")).version(1).build();
        BookDto book2 = BookDto.builder().id(UUID.randomUUID()).nameBook("Arquitetura 2").descriptionName("MUITO BOM d").price(new BigDecimal("100.00")).version(1).build();
        BookDto book3 = BookDto.builder().id(UUID.randomUUID()).nameBook("Arquitetura 3").descriptionName("MUITO BOM D").price(new BigDecimal("100.00")).version(1).build();

        this.bookMap.put(book1.getId(), book1);
        this.bookMap.put(book2.getId(), book2);
        this.bookMap.put(book3.getId(), book3);


    }

    @Override
    public List<BookDto> listBooks() {
        return new ArrayList<>(this.bookMap.values());
    }

    @Override
    public Optional<BookDto> getBookById(UUID id) {
        return Optional.of(this.bookMap.get(id));
    }

    @Override
    public BookDto saveNewBook(BookDto book) {
        book.setId(UUID.randomUUID());

        this.bookMap.put(book.getId(), book);


        return this.bookMap.get(book.getId());
    }

    @Override
    public Optional<BookDto> updateBookById(UUID id, BookDto book) {
        BookDto bookUpdated = this.bookMap.get(id);

        bookUpdated.setNameBook(book.getNameBook());
        bookUpdated.setDescriptionName(book.getDescriptionName());
        bookUpdated.setPrice(book.getPrice());
        return Optional.of(bookUpdated);
    }

    @Override
    public Boolean deleteBookById(UUID id) {
        this.bookMap.remove(id);
        return true;
    }

    @Override
    public Optional<BookDto> patchBookById(UUID id, BookDto book) {
        BookDto bookPatched = this.bookMap.get(id);


        if (!StringUtils.hasText(book.getNameBook())) {
            bookPatched.setNameBook(book.getNameBook());
        }
        if (!StringUtils.hasText(book.getDescriptionName())) {
            bookPatched.setDescriptionName(book.getDescriptionName());
        }
        if (book.getPrice() != null) {
            bookPatched.setPrice(book.getPrice());
        }

        return Optional.of(bookPatched);
    }
}
