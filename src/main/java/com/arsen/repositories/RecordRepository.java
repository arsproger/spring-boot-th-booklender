package com.arsen.repositories;

import com.arsen.models.Book;
import com.arsen.models.Record;
import com.arsen.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findByUser(User user);
}
