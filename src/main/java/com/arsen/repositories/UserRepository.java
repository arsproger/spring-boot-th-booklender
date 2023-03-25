package com.arsen.repositories;

import com.arsen.models.Book;
import com.arsen.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * from users where users.id = :books.user", nativeQuery = true)
    abstract List<User> getUserByBook(Book book);
}
