package com.arsen.controllers;

import com.arsen.dto.BookDTO;
import com.arsen.dto.BookListDTO;
import com.arsen.mappers.BookMapper;
import com.arsen.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/book")
public class BookController {
    private BookMapper bookMapper;
    private BookService bookService;

    @PostMapping("/create")
    public BookDTO createBook(@RequestBody BookDTO bookDTO) {
        return bookMapper.bookToBookDTO(bookService.saveBook(bookMapper.bookDTOtoBook(bookDTO)));
    }

    @GetMapping("/{id}")
    public BookDTO getBook(@PathVariable Long id) {
        return bookMapper.bookToBookDTO(bookService.getBookById(id));
    }

    @GetMapping("/all")
    public List<BookDTO> getAllBooks() {
        return bookMapper.bookListToBookDTOList(bookService.getAllBooks());
    }

    @PutMapping("/update/{id}")
    public BookDTO updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        return bookMapper.bookToBookDTO(bookService.updateBook(id, bookMapper.bookDTOtoBook(bookDTO)));
    }

    @DeleteMapping("/delete/{id}")
    public Long deleteBook(@PathVariable Long id) {
        return bookService.deleteBook(id);
    }

    @GetMapping("/lend/{userId}/{bookId}") // выдача книги
    public void lendBook(@PathVariable Long userId, @PathVariable Long bookId) {
        bookService.lendBook(userId, bookId);
    }

    @GetMapping("/return/{userId}/{bookId}") // возврат книги
    public void returnBook(@PathVariable Long userId, @PathVariable Long bookId) {
        bookService.returnBook(userId, bookId);
    }

    @GetMapping("/list")
    public List<BookListDTO> getAllBooksAndOwners() {
        return bookMapper.bookListToBookListDTO(bookService.showAllBooksAndOwners());
    }

}
