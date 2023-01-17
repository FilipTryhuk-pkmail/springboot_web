package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class BookController {
    private final BookRepository repository;

    BookController(BookRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/books")
    List<Book> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/books")
    Book createNewBook(@RequestBody Book newBook) {
        return repository.save(newBook);
    }

    @PutMapping("/books/{id}")
    Book replaceBook(@RequestBody Book newBook, @PathVariable Long id) {
        return repository.findById(id)
                .map(book -> {
                    book.setAuthor(newBook.getAuthor());
                    book.setTitle(newBook.getTitle());
                    book.setPublishing_date(newBook.getPublishing_date());
                    return repository.save(book);
                })
                .orElseGet(() -> {
                    return repository.save(newBook);
                });
    }
}
