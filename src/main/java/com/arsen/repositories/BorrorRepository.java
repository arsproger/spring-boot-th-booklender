package com.arsen.repositories;

import com.arsen.models.Book;
import com.arsen.models.Borror;
import com.arsen.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrorRepository extends JpaRepository<Borror, Long> {

    List<Borror> findByUser(User user);

    List<Borror> findByBook(Book book);

}
