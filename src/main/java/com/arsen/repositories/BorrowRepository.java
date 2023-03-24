package com.arsen.repositories;

import com.arsen.models.Borrow;
import com.arsen.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    List<Borrow> findByReturnedAtIsNullAndBorrowedAtBefore(LocalDateTime deadline);
    List<Borrow> findByUser(User user);
}
