package com.arsen.repositories;

import com.arsen.models.Book;
import com.arsen.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value = "select * from books where books.user_id = :users.id", nativeQuery = true)
    abstract List<Book> getBookByUser(User user);
}

