package com.arsen.controllers;

import com.arsen.dto.BookDTO;
import com.arsen.enums.Role;
import com.arsen.mappers.BookMapper;
import com.arsen.models.Book;
import com.arsen.models.User;
import com.arsen.security.DetailsUser;
import com.arsen.services.BookService;
import com.arsen.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.validation.constraints.Min;
import java.io.IOException;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/bookTh")
@EnableWebMvc
public class BookControllerTh {
    private final BookService bookService;
    private final BookMapper bookMapper;
    private final UserService userService;

    @Autowired
    public BookControllerTh(BookService bookService, BookMapper bookMapper, UserService userService) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
        this.userService = userService;
    }

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        DetailsUser detailsUser = (DetailsUser) authentication.getPrincipal();
        return detailsUser.getUser();
    }

    @GetMapping
    public String getAll(Model model,
                         @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset) {
        model.addAttribute("isNext",
                (bookService.getAllBooks().size() / (9D * (offset.doubleValue() + 1D))) > 1D);
        model.addAttribute("offset", offset);
        model.addAttribute("books", bookService.findAll(offset));
        model.addAttribute("isAdmin", getUser().getRole().equals(Role.ROLE_ADMIN));
        return "/book/books";
    }

    @GetMapping("/create")
    public String create(@ModelAttribute("book") BookDTO bookDTO, Model model) {
        model.addAttribute("isAdmin", getUser().getRole().equals(Role.ROLE_ADMIN));
        return "/book/newBook";
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

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @GetMapping("/{id}")
    public String getBook(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);

        model.addAttribute("user", userService.getUserById(getUser().getId()));
        model.addAttribute("book", book);
        model.addAttribute("records",
                book.getRecords().stream().filter(a -> a.getReturnDate() != null).collect(Collectors.toList()));
        model.addAttribute("isAdmin", getUser().getRole().equals(Role.ROLE_ADMIN));
        return "/book/show";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/bookTh";
    }

    @PutMapping("{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute BookDTO bookDTO) {
        bookService.updateBook(id, bookMapper.convertToEntity(bookDTO));
        return "redirect:/bookTh";
    }

    @GetMapping("/lend/{bookId}") // выдача книги
    public String lendBook(@PathVariable("bookId") Long bookId) {
        bookService.lendBook(getUser().getId(), bookId);

        return "redirect:/userTh/profile";
    }

    @GetMapping("/return/{bookId}") // возврат книги
    public String returnBook(@PathVariable("bookId") Long bookId) {
        bookService.returnBook(getUser().getId(), bookId);

        return "redirect:/userTh/profile";
    }

}
