package com.arsen.controllers;

import com.arsen.enums.Role;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.validation.constraints.Min;
/* рефактор кода, лишние импорты и классы нужно удалять */

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/book")
@EnableWebMvc
public class BookController {
    private final BookService bookService;
    private final UserService userService;

    @Autowired
    public BookController(BookService bookService, UserService userService) {
        this.bookService = bookService;
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

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(book.getImage());
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

    @GetMapping("/lend/{bookId}")
    public String lendBook(@PathVariable("bookId") Long bookId) {
        bookService.lendBook(getUser().getId(), bookId);

        return "redirect:/user/profile";
    }

    @GetMapping("/return/{bookId}")
    public String returnBook(@PathVariable("bookId") Long bookId) {
        bookService.returnBook(getUser().getId(), bookId);

        return "redirect:/user/profile";
    }

}
