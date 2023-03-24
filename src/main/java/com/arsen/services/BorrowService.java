package com.arsen.services;

import com.arsen.models.Borrow;
import com.arsen.models.User;
import com.arsen.repositories.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BorrowService {
    private final BorrowRepository borrowRepository;

    @Autowired
    public BorrowService(BorrowRepository borrowRepository) {
        this.borrowRepository = borrowRepository;
    }

    public List<Borrow> getAllBorrows() {
        return borrowRepository.findAll();
    }
    
    public Borrow getBorrowById(Long borrowId) {
        return borrowRepository.findById(borrowId).orElse(null);
    }
    
    public Borrow saveBorrow(Borrow borrow) {
        return borrowRepository.save(borrow);
    }
    
    public void deleteBorrow(Long borrowId) {
        borrowRepository.deleteById(borrowId);
    }

    public List<Borrow> findByReturnedAtIsNullAndBorrowedAtBefore(LocalDateTime deadline) {
        return borrowRepository.findByReturnedAtIsNullAndBorrowedAtBefore(deadline);
    }

    public List<Borrow> findByUser(User user) {
        return borrowRepository.findByUser(user);
    }

}
