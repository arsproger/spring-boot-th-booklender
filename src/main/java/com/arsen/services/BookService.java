package com.arsen.services;

import com.arsen.enums.BookStatus;
import com.arsen.models.Book;
import com.arsen.models.Record;
import com.arsen.models.User;
import com.arsen.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private UserService userService;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }
    public Book updateBook(Long id, Book book){
        Book updatedBook = getBookById(id);
        updatedBook.setName(book.getName());
        updatedBook.setAuthor(book.getAuthor());
        updatedBook.setStatus(book.getStatus());
        updatedBook.setRecords(book.getRecords());
        updatedBook.setImage(book.getImage());
        updatedBook.setUser(book.getUser());
        return bookRepository.save(updatedBook);
    }

    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public void lendBook(Long userId, Long bookId) {
        Book book = getBookById(bookId);
        User user = userService.getUserById(userId);
        book.setStatus(BookStatus.BORROWED);
        List<Book> books = new ArrayList<>();
        books.add(book);
        user.setCurrentBooks(books);
        Record record = new Record();
        record.setLoanDate(LocalDateTime.now());
        record.setBook(book);
        record.setUser(user);
    }
    public void returnBook(Long userId, Long bookId) {
        Book book = getBookById(bookId);
        User user = userService.getUserById(userId);
        book.setStatus(BookStatus.AVAILABLE);
        List<Book> books = new ArrayList<>();
        books.add(book);
        user.setPastBooks(books);

        Record record = new Record();
        record.setReturnDate(LocalDateTime.now());
        record.setBook(book);
        record.setUser(user);
    }

}
