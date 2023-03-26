package com.arsen.repositories;

import com.arsen.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value = "select Books.name, Users.full_name as borrower, books.status\n" +
            "from books join users on books.user_id = users.id " , nativeQuery = true)
    List<Book> showAll();

}

