package com.arsen.repositories;

import com.arsen.models.Book;
import com.arsen.models.BookComment;
import com.arsen.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookCommentRepository extends JpaRepository<BookComment, Long> {
    List<BookComment> findByUser(User user);
    List<BookComment>findByBook(Book book);
}
