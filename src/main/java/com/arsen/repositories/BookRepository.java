package com.arsen.repositories;

import com.arsen.models.Book;
import com.arsen.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value = "select Books.name as book_name, books.status as book_status\n" +
            "from books join users on books.user_id = users.id" , nativeQuery = true)
    List<Book> showAll();

    @Query(value = "select Books.description as book_description, Books.image as book_image\n" +
            "from books where id = ?" , nativeQuery = true)
    Book showDescAndImg(Long id);

    List<Book> findByUser(User user);

}