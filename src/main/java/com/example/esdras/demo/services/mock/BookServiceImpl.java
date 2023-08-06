package com.example.esdras.demo.services.mock;

import com.example.esdras.demo.dto.BookDto;
import com.example.esdras.demo.services.interfaces.BookService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

//COMO E INJETAVEL

@Service
public class BookServiceImpl implements BookService {

    private final Map<UUID, BookDto> bookMap;


    public BookServiceImpl(

    )
    {
        this.bookMap=new HashMap<>();

        BookDto book1 = BookDto.builder().id(UUID.randomUUID()).nameBook("Arquitetura").
                descriptionName("MUITO BOM").price(new BigDecimal("100.00")).version(1).build();
        BookDto book2 = BookDto.builder().id(UUID.randomUUID()).nameBook("Arquitetura 2").
                descriptionName("MUITO BOM d").price(new BigDecimal("100.00")).version(1).build();
        BookDto book3 = BookDto.builder().id(UUID.randomUUID()).nameBook("Arquitetura 3").
                descriptionName("MUITO BOM D").price(new BigDecimal("100.00")).version(1).build();

        this.bookMap.put(book1.getId(),book1);
        this.bookMap.put(book2.getId(),book2);
        this.bookMap.put(book3.getId(),book3);


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

        this.bookMap.put(book.getId(),book);


        return this.bookMap.get(book.getId());
    }

    @Override
    public BookDto updateBookById(UUID id, BookDto book) {
        var bookUpdated= this.bookMap.get(id);
        if(bookUpdated!=null)
        {
            bookUpdated.setNameBook(book.getNameBook());
            bookUpdated.setDescriptionName(book.getDescriptionName());
            bookUpdated.setPrice(book.getPrice());
            bookUpdated.setVersion(book.getVersion());
        }


        return  bookUpdated;
    }

    @Override
    public BookDto deleteBookById(UUID id) {
        var bookDeleted= this.bookMap.get(id);
        if(bookDeleted!=null)
            this.bookMap.remove(id);
        return  bookDeleted;
    }

    @Override
    public BookDto patchBookById(UUID id, BookDto book) {
        var bookPatched= this.bookMap.get(id);
        if(bookPatched!=null)
        {
            if(book.getNameBook()!=null)
                bookPatched.setNameBook(book.getNameBook());
            if(book.getDescriptionName()!=null)
                bookPatched.setDescriptionName(book.getDescriptionName());
            if(book.getPrice()!=null)
                bookPatched.setPrice(book.getPrice());
            if(book.getVersion()!=null)
                bookPatched.setVersion(book.getVersion());
        }
        return  bookPatched;
    }
}