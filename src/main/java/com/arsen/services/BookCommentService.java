package com.arsen.services;

import com.arsen.models.Book;
import com.arsen.models.BookComment;
import com.arsen.repositories.BookCommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookCommentService {
    private final BookCommentRepository bookCommentRepository;



    public BookCommentService(BookCommentRepository bookCommentRepository){
        this.bookCommentRepository = bookCommentRepository;
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

}
