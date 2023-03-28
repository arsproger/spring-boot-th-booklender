package com.arsen.services;

import com.arsen.enums.BookStatus;
import com.arsen.models.Book;
import com.arsen.models.Record;
import com.arsen.models.User;
import com.arsen.repositories.BookRepository;
import com.arsen.repositories.RecordRepository;
import com.arsen.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    UserService userService;
    RecordService recordService;
    RecordRepository recordRepository;

    @Autowired
    public BookService(BookRepository bookRepository, UserRepository userRepository, UserService userService, RecordService recordService, RecordRepository recordRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.recordService = recordService;
        this.recordRepository = recordRepository;
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
        updatedBook.setDescription(book.getDescription());
        return bookRepository.save(updatedBook);
    }

    public Book saveBook(Book book) {
        book.setStatus(BookStatus.AVAILABLE);
        return bookRepository.save(book);
    }

    public Long deleteBook(Long id) {
        bookRepository.deleteById(id);
        return id;
    }

    public void lendBook(Long userId, Long bookId) {
        Book book = getBookById(bookId); // находим книгу
        User user = userService.getUserById(userId); //находим юзера
        if (book.getStatus() == BookStatus.BORROWED){
            System.out.println("Книга занята");
            return;
        }
        book.setStatus(BookStatus.BORROWED); // статус книги "заимствован"
        book.setUser(user); // присваиваем книге владельца
        List<Book> books = new ArrayList<>();
        books.add(book);
        user.setCurrentBooks(books); // присваиваем юзеру список текущих книг с заимствованной книгой
        userService.updateUser(userId, user); // апдейтим юзера, чтобы сохранить данные
        Record record = new Record();
        record.setLoanDate(LocalDateTime.now()); // создаем запись и задаем время займа книги
        record.setBook(book); // добавляем книгу в запись
        record.setUser(user); // добавляем юзера в запись
        recordService.saveRecord(record); // сохраняем новую запись
    }
    public void returnBook(Long userId, Long bookId) {
        Book book = getBookById(bookId); // находим книгу
        User user = userService.getUserById(userId); //находим юзера
        if (book.getStatus() == BookStatus.AVAILABLE){
            System.out.println("Книга уже возвращена");
            return;
        }
        book.setStatus(BookStatus.AVAILABLE); // статус книги "доступен"
        book.setUser(null);
        List<Book> books = new ArrayList<>();
        books.add(book);
        user.setPastBooks(books); // присваиваем юзеру список прошлых книг с возвращенной книгой
        Record record = new Record();
        record.setReturnDate(LocalDateTime.now()); // создаем запись и задаем время возвращения книги
        record.setBook(book); // добавляем книгу в запись
        record.setUser(user); // добавляем юзера в запись
        List<Record> oldRecords = recordRepository.findByUser(user);
        Record lastRecord = null;
        for (Record record1 : oldRecords) {
            if (lastRecord == null || record.getLoanDate().isAfter(lastRecord.getLoanDate())) {
                lastRecord = record1;
            }
        }
        assert lastRecord != null;
        Long record_id = lastRecord.getId();
        recordService.updateRecord(record_id,record); //
    }

    public StringBuilder showAllBooksAndOwners(){
//        return bookRepository.showAll();
        StringBuilder stringBuilder = new StringBuilder();
        for(Book book : bookRepository.findAll()) {
            stringBuilder.append(book.getName() + " " + book.getStatus() + " " +
                    userRepository.findById(book.getUser().getId()).get().getFullName() + "\n");
        }
        return stringBuilder;
    }

    public Book showDescriptionAndImage(Long id){
        return bookRepository.showDescAndImg(id);
    }

}
