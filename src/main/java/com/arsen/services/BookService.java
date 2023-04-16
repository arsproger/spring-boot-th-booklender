package com.arsen.services;

import com.arsen.enums.BookStatus;
import com.arsen.models.Book;
import com.arsen.models.Record;
import com.arsen.models.User;
import com.arsen.repositories.BookRepository;
import com.arsen.repositories.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final UserService userService;
    private final RecordService recordService;
    private final RecordRepository recordRepository;

    @Autowired
    public BookService(BookRepository bookRepository, UserService userService, RecordService recordService, RecordRepository recordRepository) {
        this.bookRepository = bookRepository;
        this.userService = userService;
        this.recordService = recordService;
        this.recordRepository = recordRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Page<Book> findAll(Integer offset) {
        return bookRepository.findAll(PageRequest.of(offset, 9, Sort.by("id").ascending()));
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book updateBook(Long id, Book book) {
        Book updatedBook = getBookById(id);
        updatedBook.setName(book.getName());
        updatedBook.setAuthor(book.getAuthor());
        updatedBook.setStatus(book.getStatus());
        updatedBook.setRecords(book.getRecords());
        updatedBook.setImage(book.getImage());
        updatedBook.setUser(book.getUser());
        updatedBook.setDescription(book.getDescription());
        return bookRepository.save(updatedBook);
    }

    public Book saveBook(Book book) {
        book.setStatus(BookStatus.AVAILABLE);
        if (book.getImage() == null || book.getImage().length == 0) {
            try {
                book.setImage(Files.readAllBytes(Paths.get("C:\\Users\\user\\Downloads\\" +
                        "spring-boot-th-booklender\\spring-boot-th-booklender\\src\\main\\resources\\static\\image\\default-book.jpg")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return bookRepository.save(book);
    }

    public Long deleteBook(Long id) {
        bookRepository.deleteById(id);
        return id;
    }


    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void lendBook(Long userId, Long bookId) {
        Book book = getBookById(bookId);
        User user = userService.getUserById(userId);
        if (book.getStatus() == BookStatus.BORROWED) {
            System.out.println("Книга занята");
            return;
        }
        List<Book> books = user.getCurrentBooks();

        if (books.size() >= 2) {
            System.out.println("Error");
            return;
        }

        book.setStatus(BookStatus.BORROWED);
        book.setUser(user);
        books.add(book);
        userService.updateUser(userId, user);
        Record record = new Record();
        record.setLoanDate(LocalDateTime.now());
        record.setBook(book);
        record.setUser(user);
        recordService.saveRecord(record);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void returnBook(Long userId, Long bookId) {
        Book book = getBookById(bookId);
        User user = userService.getUserById(userId);
        if (book.getStatus() == BookStatus.AVAILABLE) {
            System.out.println("Книга уже возвращена");
            return;
        }
        book.setStatus(BookStatus.AVAILABLE);
        book.setUser(null);
        List<Book> books = user.getCurrentBooks();
        books.remove(book);
        user.getPastBooks().add(book);
        Record record = recordRepository.findById(recordRepository.findByBookId(bookId)).orElse(null);

        if (record != null) {
            record.setReturnDate(LocalDateTime.now());
        }
        recordService.saveRecord(record);
    }

}
