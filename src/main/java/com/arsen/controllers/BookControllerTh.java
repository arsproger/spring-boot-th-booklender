package com.arsen.controllers;

import com.arsen.dto.BookDTO;
import com.arsen.mappers.BookMapper;
import com.arsen.models.Book;
import com.arsen.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;

@Controller
@AllArgsConstructor
@RequestMapping("/bookTh")
@EnableWebMvc
public class BookControllerTh {
    private BookService bookService;
    private BookMapper bookMapper;

    @GetMapping("/create")
    public String create(@ModelAttribute("book") BookDTO bookDTO) {
        return "/dev/newBook";
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(book.getImage());
    }

    @PostMapping("/new")
    public String addBook(@ModelAttribute("book") BookDTO bookDTO) throws IOException {
        Book book = new Book();
        book.setName(bookDTO.getName());
        book.setAuthor(bookDTO.getAuthor());
        book.setDescription(bookDTO.getDescription());
        book.setImage(bookDTO.getImage().getBytes());

        bookService.saveBook(book);

        return "redirect:/bookTh";
    }

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
