package com.example.bookmanager.service;

import com.example.bookmanager.model.Book;
import com.example.bookmanager.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // Get all books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Get book by ID
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    // Create a new book
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    // Update existing book
    public Optional<Book> updateBook(Long id, Book updatedBook) {
        return bookRepository.findById(id).map(existingBook -> {
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setIsbn(updatedBook.getIsbn());
            existingBook.setPrice(updatedBook.getPrice());
            existingBook.setPublishedYear(updatedBook.getPublishedYear());
            existingBook.setGenre(updatedBook.getGenre());
            return bookRepository.save(existingBook);
        });
    }

    // Delete book by ID
    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Search by author
    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    // Search by genre
    public List<Book> getBooksByGenre(String genre) {
        return bookRepository.findByGenreIgnoreCase(genre);
    }

    // Search by title
    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }
}
