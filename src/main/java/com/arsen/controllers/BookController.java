package com.arsen.controllers;

import com.arsen.dto.BookDTO;
import com.arsen.mappers.BookMapper;
import com.arsen.models.Book;
import com.arsen.services.BookService;
import com.arsen.services.RecordService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/book")
public class BookController {
    BookMapper bookMapper;
    BookService bookService;
    RecordService recordService;

    @PostMapping("/create")
    public BookDTO createBook(@RequestBody BookDTO bookDTO){
        Book book = bookMapper.bookDTOtoBook(bookDTO);
        bookService.saveBook(book);
        return bookMapper.bookToBookDTO(book);
    }

    @GetMapping("/{id}")
    public BookDTO getBook(@PathVariable Long id){
        Book book = bookService.getBookById(id);
        return bookMapper.bookToBookDTO(book);
    }

    @GetMapping("/all")
    public List<BookDTO> getAllBooks(){
        List<Book> books = bookService.getAllBooks();
        return bookMapper.bookListToBookDTOList(books);
    }

    @PutMapping("/update/{id}")
    public BookDTO updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO){
        Book book = bookMapper.bookDTOtoBook(bookDTO);
        Book updatedBook = bookService.updateBook(id, book);
        return bookMapper.bookToBookDTO(updatedBook);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
    }

    @GetMapping("/lend/{userId}/{bookId}") // выдача книги
    public void lendBook(@PathVariable Long userId, @PathVariable Long bookId){
        bookService.lendBook(userId, bookId);
    }

    @GetMapping("/return/{userId}/{bookId}") // возврат книги
    public void returnBook(@PathVariable Long userId, @PathVariable Long bookId){
        bookService.returnBook(userId, bookId);
    }

    @GetMapping("/list")
    public List<Book> getAllBooksAndOwners(){
        List<Book> books = bookService.showAllBooksAndOwners();
        return books;//bookMapper.bookListToBookListDTO(books);
    }

}
