package com.arsen.repositories;

import com.arsen.models.Record;
import com.arsen.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    @Query(value = "select id from records where book_id = ? and return_date IS NULL", nativeQuery = true)
    Long findByBookId(Long id);

    List<Record> findByUser(User user);
}
