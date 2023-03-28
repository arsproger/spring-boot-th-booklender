package com.arsen.controllers;

import com.arsen.dto.BookDTO;
import com.arsen.mappers.BookMapper;
import com.arsen.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@AllArgsConstructor
@RequestMapping("/bookTh")
public class BookControllerTh {
    private BookService bookService;
    private BookMapper bookMapper;

    @PostMapping("/books")
    public String addBook(@ModelAttribute("book") BookDTO bookDTO, @RequestParam("image") MultipartFile file) throws IOException {
        bookDTO.setImage(file.getBytes());
        bookService.saveBook(bookMapper.convertToEntity(bookDTO));

        return "redirect:/books";
    }

}
