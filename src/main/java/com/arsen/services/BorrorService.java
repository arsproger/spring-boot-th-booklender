package com.arsen.services;


import com.arsen.models.Book;
import com.arsen.models.Borror;
import com.arsen.models.User;
import com.arsen.repositories.BorrorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrorService {
    private final BorrorRepository borrorRepository;

    public BorrorService(BorrorRepository borrorRepository) {
        this.borrorRepository = borrorRepository;
    }

    public List<Borror> getBorrorByUser(User user) {
        return borrorRepository.findByUser(user);
    }

    public List<Borror> getBorrorByBook(Book book) {
        return borrorRepository.findByBook(book);
    }
}
