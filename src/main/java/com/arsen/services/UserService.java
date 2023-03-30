package com.arsen.services;

import com.arsen.enums.Role;
import com.arsen.models.Book;
import com.arsen.models.Record;
import com.arsen.models.User;
import com.arsen.repositories.BookRepository;
import com.arsen.repositories.RecordRepository;
import com.arsen.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final RecordRepository recordRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BookRepository bookRepository, RecordRepository recordRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.recordRepository = recordRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);
        if (user.getImage() == null) {
            try {
                user.setImage(defaultImage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return userRepository.save(user);
}

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public byte[] defaultImage() throws IOException {
        return Files.readAllBytes(Paths.get("C:\\Users\\user\\Downloads\\" +
                "spring-boot-th-booklender\\spring-boot-th-booklender\\src\\main\\resources\\static\\image\\default.jpg"));
    }

    public Long deleteUser(Long id) {
        userRepository.deleteById(id);
        return id;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User updateUser(Long id, User user) {
        User updatedUser = getUserById(id);
        updatedUser.setEmail(user.getEmail());
        updatedUser.setPassword(user.getPassword());
        updatedUser.setFullName(user.getFullName());
        updatedUser.setDateOfBirth(user.getDateOfBirth());
        return userRepository.save(updatedUser);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Record> currentBooks(User user) {
        return recordRepository.findByUser(user)
                .stream().filter(a -> a.getReturnDate() == null).collect(Collectors.toList());
    }

//    @Transactional(isolation = Isolation.SERIALIZABLE)
//    public List<Book> currentBooks(User user) {
//        return bookRepository.findByUser(user);
//    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Record> pastBooks(User user) {
        return recordRepository.findByUser(user)
                .stream().filter(a -> a.getReturnDate() != null).collect(Collectors.toList());
    }

//    @Transactional(isolation = Isolation.SERIALIZABLE)
//    public List<Book> cur(User user) {
//        return bookRepository.findByUser(user);
//    }

//    public List<Book> pas(User user) {
//
//    }
}