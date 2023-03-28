package com.arsen.controllers;

import com.arsen.dto.BookDTO;
import com.arsen.mappers.BookMapper;
import com.arsen.models.Book;
import com.arsen.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookMapper bookMapper;
    private final BookService bookService;

    @Autowired
    public BookController(BookMapper bookMapper, BookService bookService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }

    @PostMapping("/create")
    public BookDTO createBook(@RequestBody BookDTO bookDTO){
        Book book = bookMapper.convertToEntity(bookDTO);
        bookService.saveBook(book);
        return bookMapper.convertToDTO(book);
    }

    @GetMapping("/{id}")
    public BookDTO getBook(@PathVariable Long id){
        Book book = bookService.getBookById(id);
        return bookMapper.convertToDTO(book);
    }

    @GetMapping("/all")
    public List<BookDTO> getAllBooks(){
        return bookService.getAllBooks().stream().map(
                bookMapper::convertToDTO).collect(Collectors.toList());
    }

    @PutMapping("/update/{id}")
    public BookDTO updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO){
        Book book = bookMapper.bookDTOtoBook(bookDTO);
        Book updatedBook = bookService.updateBook(id, book);
        return bookMapper.convertToDTO(updatedBook);
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
    public StringBuilder getAllBooksAndOwners(){
        return bookService.showAllBooksAndOwners();
        //bookMapper.bookListToBookListDTO(books);
    }

    @GetMapping("/show/{id}")
    public Book getDescriptionAndImageById(@PathVariable Long id){
        return bookService.showDescriptionAndImage(id);
    }

}
