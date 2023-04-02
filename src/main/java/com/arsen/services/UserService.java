package com.arsen.services;

import com.arsen.enums.Role;
import com.arsen.enums.UserStatus;
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

import javax.mail.MessagingException;
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
    private final EmailService emailService;

    @Autowired
    public UserService(UserRepository userRepository, BookRepository bookRepository, RecordRepository recordRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.recordRepository = recordRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
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
        user.setStatus(UserStatus.INACTIVE);
        if (user.getImage() == null) {
            try {
                user.setImage(defaultImage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        String linkForActive = generateLinkForUserActive(user.getEmail());
        User saveUser= userRepository.save(user);
        try {
            emailService.sendActivationEmail(user.getEmail(), linkForActive);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return saveUser;
    }


    private String generateLinkForUserActive(String email) {
        return "http://localhost:8080/userTh/active?email=" + email;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public byte[] defaultImage() throws IOException {
        return Files.readAllBytes(Paths.get("src/main/resources/static/image/default.jpg").toAbsolutePath());
    }

    public Long deleteUser(Long id) {
        userRepository.deleteById(id);
        return id;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public Long activeUser(String email){
        User user = userRepository.findByEmail(email);

        user.setStatus(UserStatus.ACTIVE);
        return userRepository.save(user).getId();
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