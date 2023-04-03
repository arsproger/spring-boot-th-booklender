package com.arsen.services;

import com.arsen.models.Book;
import com.arsen.models.BookComment;
import com.arsen.models.User;
import com.arsen.repositories.BookCommentRepository;
import com.arsen.repositories.BookRepository;
import com.arsen.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookCommentService {
    private final BookCommentRepository bookCommentRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final BookService bookService;


    @Autowired
    public BookCommentService(BookCommentRepository bookCommentRepository, BookRepository bookRepository, UserRepository userRepository, UserService userService, BookService bookService) {
        this.bookCommentRepository = bookCommentRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.bookService = bookService;
    }

    public List<BookComment> getAllComments() {
        return bookCommentRepository.findAll();
    }

    public BookComment getCommentById(Long id) {
        return bookCommentRepository.findById(id).orElse(null);
    }

    public BookComment createComment(BookComment bookComment) {
        return bookCommentRepository.save(bookComment);
    }

    public BookComment updateComment(BookComment bookComment, Long id) {
        BookComment updateComment = getCommentById(id);
        updateComment.setGrade(bookComment.getGrade());
        updateComment.setTitle(bookComment.getTitle());
        updateComment.setDescription(bookComment.getDescription());
        return bookCommentRepository.save(updateComment);
    }

    public Long deleteComment(Long id) {
        bookCommentRepository.deleteById(id);
        return id;
    }

    public BookComment saveComment(BookComment bookComment) {
        return bookCommentRepository.save(bookComment);
    }

//    public BookComment addComment(BookComment bookComment, Long userId, Long bookId){
//        User user = userService.getUserById(userId);
//        List<BookComment> comments = new ArrayList<>();
//        comments.add(bookComment);
//        user.setComments(comments);
//        Book book = bookService.getBookById(bookId);
//        List<BookComment> comments1 = new ArrayList<>();
//        comments1.add(bookComment);
//        book.setComments(comments1);
//        return bookComment;
//    }

    public BookComment addComment(BookComment bookComment, Long userId, Long bookId) {
        User user = userService.getUserById(userId);
        Book book = bookService.getBookById(bookId);
        bookComment.setUser(user);
        bookComment.setBook(book);
        bookCommentRepository.save(bookComment);
        return bookComment;
    }

}
