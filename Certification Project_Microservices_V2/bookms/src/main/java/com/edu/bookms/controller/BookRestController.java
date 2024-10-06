package com.edu.bookms.controller;

import com.edu.bookms.model.Book;
import com.edu.bookms.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class BookRestController {

    @Autowired
    BookRepository bookRepository;

    @GetMapping(path = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> getUsersList() {
        return bookRepository.findAll();
    }

    @GetMapping(path = "/books/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> getSingleUsers(@PathVariable Integer id) {
        Optional<Book> bookFound = bookRepository.findById(id);
        if(bookFound.isPresent()) {
            return ResponseEntity.ok(bookFound.get());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/bookByAuthor", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> findBookByAuthor(@RequestParam String author) {
        Book book = bookRepository.findByAuthor(author);
        if(book != null){
            return ResponseEntity.ok(book);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(path = "/books", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> addBook(@RequestBody Book book) throws URISyntaxException {
        Book bookSaved = bookRepository.save(book);
        return ResponseEntity.created(new URI(bookSaved.getIsbn().toString())).body(bookSaved);
    }

    @PutMapping(path = "/books", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> updateBook(@RequestBody Book book) throws URISyntaxException {
        Book bookSaved = bookRepository.save(book);
        return ResponseEntity.created(new URI(bookSaved.getIsbn().toString())).body(bookSaved);
    }

    @DeleteMapping(path = "/books", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> deleteBook(@RequestBody Book book) throws URISyntaxException {
        Optional<Book> bookFound = bookRepository.findById(book.getIsbn());
        if(bookFound.isPresent()) {
            bookRepository.delete(book);
            return ResponseEntity.ok(book);
        }

        return ResponseEntity.notFound().build();
    }
}
