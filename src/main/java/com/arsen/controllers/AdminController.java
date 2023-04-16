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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.io.IOException;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final BookService bookService;
    private final BookMapper bookMapper;

    @Autowired
    public AdminController(UserService userService, BookService bookService, BookMapper bookMapper) {
        this.userService = userService;
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        DetailsUser detailsUser = (DetailsUser) authentication.getPrincipal();
        return detailsUser.getUser();
    }


    @GetMapping("/user")
    public String main(Model model,
                       @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset) {
        model.addAttribute("isNext",
                (userService.getAllUsers().size() / (9D * (offset.doubleValue() + 1D))) > 1D);
        model.addAttribute("offset", offset);
        model.addAttribute("isAdmin", getUser().getRole().equals(Role.ROLE_ADMIN));
        model.addAttribute("users", userService.findAll(offset));
        return "/user/users";
    }

    @GetMapping("/user/profile/{id}")
    public String profile(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("isAdmin", getUser().getRole().equals(Role.ROLE_ADMIN));
        model.addAttribute("curBooks",
                userService.currentBooks(userService.getUserById(id)));
        model.addAttribute("pastBooks",
                userService.pastBooks(userService.getUserById(id)));

        return "redirect:/user/profile?id=" + id;
    }

    @GetMapping("/book/create")
    public String create(@ModelAttribute("book") BookDTO bookDTO, Model model) {
        model.addAttribute("isAdmin", getUser().getRole().equals(Role.ROLE_ADMIN));
        return "/book/newBook";
    }

    @PostMapping("/book/new")
    public String addBook(@ModelAttribute("book") BookDTO bookDTO) throws IOException {
        Book book = new Book();
        book.setName(bookDTO.getName());
        book.setAuthor(bookDTO.getAuthor());
        book.setDescription(bookDTO.getDescription());
        book.setImage(bookDTO.getImage().getBytes());

        bookService.saveBook(book);

        return "redirect:/book";
    }

    @DeleteMapping("/book/delete/{id}")
    public String delete(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/book";
    }

    @PutMapping("/book{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute BookDTO bookDTO) {
        bookService.updateBook(id, bookMapper.convertToEntity(bookDTO));
        return "redirect:/book";
    }

}
