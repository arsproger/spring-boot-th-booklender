package com.arsen.controllers;

import com.arsen.dto.BookDTO;
import com.arsen.enums.BookStatus;
import com.arsen.mappers.BookMapper;
import com.arsen.models.Book;
import com.arsen.repositories.BookRepository;
import com.arsen.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@AllArgsConstructor
@RequestMapping("/bookTh")
public class BookControllerTh {
    private BookService bookService;
    private BookRepository bookRepository;
    private BookMapper bookMapper;


    @GetMapping
    public String main(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "/dev/test";
    }

    @GetMapping("/{id}")
    public String getBook(@PathVariable Long id, Model model){
        Book book = bookService.getBookById(id);
        model.addAttribute("book" , bookMapper.convertToDTO(book));
        return "/dev/book/show";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        bookService.deleteBook(id);
        return "redirect:/bookTh";
    }

    @PutMapping("{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute BookDTO bookDTO) {
        bookService.updateBook(id, bookMapper.convertToEntity(bookDTO));
        return "redirect:/bookTh";
    }
    
}
