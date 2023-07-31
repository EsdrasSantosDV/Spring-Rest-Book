package com.example.esdras.demo.services;

import com.example.esdras.demo.model.Book;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

//COMO E INJETAVEL

@Service
public class BookServiceImpl implements BookService {

    private final Map<UUID,Book> bookMap;


    public BookServiceImpl(

    )
    {
        this.bookMap=new HashMap<>();

        Book book1 = Book.builder().id(UUID.randomUUID()).nameBook("Arquitetura").
                descriptionName("MUITO BOM").price(new BigDecimal("100.00")).version(1).build();
        Book book2 = Book.builder().id(UUID.randomUUID()).nameBook("Arquitetura 2").
                descriptionName("MUITO BOM d").price(new BigDecimal("100.00")).version(1).build();
        Book book3 = Book.builder().id(UUID.randomUUID()).nameBook("Arquitetura 3" ).
                descriptionName("MUITO BOM D").price(new BigDecimal("100.00")).version(1).build();

        this.bookMap.put(book1.getId(),book1);
        this.bookMap.put(book2.getId(),book2);
        this.bookMap.put(book3.getId(),book3);


    }

    @Override
    public List<Book> listBooks() {
        return new ArrayList<>(this.bookMap.values());
    }

    @Override
    public Book getBookById(UUID id) {
        return this.bookMap.get(id);
    }

    @Override
    public Book saveNewBook(Book book) {
        book.setId(UUID.randomUUID());

        this.bookMap.put(book.getId(),book);


        return this.bookMap.get(book.getId());
    }

    @Override
    public void updateBookById(UUID id, Book book) {

    }

    @Override
    public Book deleteBookById(UUID id) {
        var bookDeleted= this.bookMap.get(id);
        if(bookDeleted!=null)
            this.bookMap.remove(id);
        return  bookDeleted;
    }

    @Override
    public void patchBookById(UUID id, Book book) {

    }
}
