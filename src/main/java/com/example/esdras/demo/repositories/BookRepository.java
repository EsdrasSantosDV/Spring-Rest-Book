package com.example.esdras.demo.repositories;

import com.example.esdras.demo.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;

public interface BookRepository extends JpaRepository<BookEntity, UUID> {
}
