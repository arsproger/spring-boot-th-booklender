package com.arsen.services;

import com.arsen.models.Book;
import com.arsen.models.BookComment;
import com.arsen.models.User;
import com.arsen.repositories.BookCommentRepository;
import com.arsen.repositories.BookRepository;
import com.arsen.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookCommentService {
    private final BookCommentRepository bookCommentRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;



    @Autowired
    public BookCommentService(BookCommentRepository bookCommentRepository, BookRepository bookRepository, UserRepository userRepository){
        this.bookCommentRepository = bookCommentRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public List<BookComment> getAllComments(){
        return bookCommentRepository.findAll();
    }

    public BookComment getCommentById(Long id){
        return bookCommentRepository.findById(id).orElse(null);
    }

    public BookComment createComment(BookComment bookComment){
        return bookCommentRepository.save(bookComment);
    }

    public BookComment updateComment(BookComment bookComment, Long id){
        BookComment updateComment = getCommentById(id);
        updateComment.setGrade(bookComment.getGrade());
        updateComment.setTitle(bookComment.getTitle());
        updateComment.setDescription(bookComment.getDescription());
        return bookCommentRepository.save(updateComment);
    }
    public Long deleteComment(Long id){
        bookCommentRepository.deleteById(id);
        return id;
    }

    public BookComment saveComment(BookComment bookComment){
        return  bookCommentRepository.save(bookComment);
    }


//    public void saveComment(BookComment bookComment, User user, Book book){
//        BookComment bookComment1 = new BookComment();
//
//        bookComment1.setGrade(bookComment.getGrade());
//        bookComment1.setTitle(bookComment.getTitle());
//        bookComment1.setDescription(bookComment1.getDescription());
//        bookComment1.setBook(book);
//        bookComment1.setUser(user);
//        bookCommentRepository.save(bookComment1);
//    }

}
