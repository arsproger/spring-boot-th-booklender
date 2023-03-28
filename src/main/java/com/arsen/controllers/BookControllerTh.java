package com.arsen.controllers;

import com.arsen.dto.BookDTO;
import com.arsen.mappers.BookMapper;
import com.arsen.models.Book;
import com.arsen.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/bookTh")
public class BookControllerTh {
    private BookService bookService;
    private BookMapper bookMapper;


    @GetMapping
    public String main(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "/dev/book/all";
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
