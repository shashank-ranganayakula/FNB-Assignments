package com.example.bookmanager;

import com.example.bookmanager.model.Book;
import com.example.bookmanager.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookManagerApplication.class, args);
    }

    // Seed some sample data on startup
    @Bean
    CommandLineRunner initDatabase(BookRepository repository) {
        return args -> {
            repository.save(new Book(null, "Clean Code", "Robert C. Martin", "978-0132350884", 35.99, 2008, "Programming"));
            repository.save(new Book(null, "The Pragmatic Programmer", "David Thomas", "978-0135957059", 49.99, 2019, "Programming"));
            repository.save(new Book(null, "Design Patterns", "Gang of Four", "978-0201633610", 54.99, 1994, "Programming"));
            System.out.println("✅ Sample books loaded into database.");
        };
    }
}
