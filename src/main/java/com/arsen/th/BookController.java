package com.arsen.th;

import com.arsen.dto.BookDTO;
import com.arsen.mappers.BookMapper;
import com.arsen.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Collectors;

@Controller("bookClass")
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final BookMapper bookMapper;

    @Autowired
    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("books", bookService.getAllBooks().stream()
                .map(bookMapper::convertToDTO).collect(Collectors.toList()));
        return "/dev/test";
    }

    @GetMapping("/new")
    public String addBook(@ModelAttribute("book") BookDTO bookDTO) {
        return "/dev/newBook";
    }

    @PostMapping
    public String add(@ModelAttribute("book") BookDTO bookDTO, @RequestParam("image") MultipartFile file) throws IOException {
        bookDTO.setImage(file.getBytes());
        bookService.saveBook(bookMapper.convertToEntity(bookDTO));

        return "redirect:/books";
    }
}
