package com.library_management_system.project.services;

import com.library_management_system.project.dto.BookDto;
import com.library_management_system.project.entities.Book;
import com.library_management_system.project.repositories.BookReporitory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService{
    private final BookReporitory bookReporitory;

    public BookService(BookReporitory bookReporitory) {
        this.bookReporitory = bookReporitory;
    }

    public List<Book> getAllBooks() {
        return bookReporitory.findAll();
    }

    public Book getBookById(Long id) {
        Optional<Book> book = bookReporitory.findById(id);
        return book.orElseThrow(RuntimeException::new);
    }

    public Book addBook(BookDto bookDto) {
        Book book = new Book();

        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setIsbn(bookDto.getIsbn());
        book.setQuantity(bookDto.getQuantity());
        book.setAvailable(bookDto.getAvailable());

        return bookReporitory.save(book);
    }

    public Book updateBook(Long id, BookDto bookDto) {
        Book book = bookReporitory.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));

        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setIsbn(bookDto.getIsbn());
        book.setQuantity(bookDto.getQuantity());
        book.setAvailable(bookDto.getAvailable());

        return bookReporitory.save(book);
    }

    public void deleteBookById(Long id) {
        bookReporitory.deleteById(id);
    }
}
