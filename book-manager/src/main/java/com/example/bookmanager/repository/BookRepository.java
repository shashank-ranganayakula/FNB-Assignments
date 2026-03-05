package com.example.bookmanager.repository;

import com.example.bookmanager.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAuthorContainingIgnoreCase(String author);

    List<Book> findByGenreIgnoreCase(String genre);

    Optional<Book> findByIsbn(String isbn);

    List<Book> findByTitleContainingIgnoreCase(String title);
}
