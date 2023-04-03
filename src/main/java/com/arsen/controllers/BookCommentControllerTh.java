package com.arsen.controllers;

import com.arsen.dto.CommentDTO;
import com.arsen.mappers.BookCommentMapper;
import com.arsen.models.Book;
import com.arsen.models.BookComment;
import com.arsen.models.User;
import com.arsen.repositories.BookCommentRepository;
import com.arsen.repositories.BookRepository;
import com.arsen.repositories.UserRepository;
import com.arsen.services.BookCommentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@EnableWebMvc
@AllArgsConstructor
@RequestMapping("/commentTh")
public class BookCommentControllerTh {
    private BookCommentMapper bookCommentMapper;
    private BookCommentService bookCommentService;
    private final UserRepository userRepository;
    private final BookCommentRepository bookCommentRepository;
    private final BookRepository bookRepository;


    @PostMapping("/new")
    public String addComment(@ModelAttribute("book-comments") CommentDTO commentDTO){
        BookComment bookComment = new BookComment();
        bookComment.setGrade(bookComment.getGrade());
        bookComment.setTitle(bookComment.getTitle());
        bookComment.setDescription(bookComment.getDescription());
        bookCommentService.saveComment(bookComment);
        return null;
    }


    @GetMapping("/{id}")
    public String getCommentById(@PathVariable Long id, Model model){
        BookComment bookComment = bookCommentService.getCommentById(id);
        model.addAttribute("book-comment", bookComment);
        return null;
    }

    @PostMapping("/{id}/update")
    public String commentUpdate(@PathVariable Long id, @ModelAttribute CommentDTO commentDTO){
        BookComment bookComment = bookCommentService.getCommentById(id);

        BookComment updateComment = bookCommentMapper.convertToEntity(commentDTO);
        updateComment.setId(id);
        updateComment.setGrade(bookComment.getGrade());
        updateComment.setTitle(bookComment.getTitle());
        updateComment.setDescription(bookComment.getDescription());
        bookCommentService.saveComment(updateComment);

        return null;
    }
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id){
        bookCommentService.deleteComment(id);
        return null;
    }

    @GetMapping("/user/{userId}/comments")
    public List<BookComment> getCommentByUserId(@PathVariable Long userId){
        User user = userRepository.findById(userId).orElse(null);
        return bookCommentRepository.findByUser(user);
    }

    @GetMapping("/books/{bookId}/comments")
    public List<BookComment> getCommentByBookId(@PathVariable Long bookId){
        Book book = bookRepository.findById(bookId).orElse(null);
        return bookCommentRepository.findByBook(book);
    }

//    public List<BookComment> getCommentByUserId(User user){
//        user = userRepository.findById(userId).orElse(null);
//        List<BookComment> comments = getCommentByUserId(user);
//        return bookCommentRepository.findByUser(user);
//
//    }
}