package com.arsen.services;

import com.arsen.enums.Role;
import com.arsen.models.Record;
import com.arsen.models.User;
import com.arsen.repositories.RecordRepository;
import com.arsen.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RecordRepository recordRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Autowired
    public UserService(UserRepository userRepository, RecordRepository recordRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.recordRepository = recordRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Page<User> findAll(Integer offset) {
        return userRepository.findAll(PageRequest.of(offset, 9, Sort.by("id").ascending()));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);
        if (user.getImage() == null || user.getImage().length == 0) {
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

    public User updateUser(Long id, User updatedUser) {
        User user = getUserById(id);
        user.setFullName(updatedUser.getFullName());
        user.setDateOfBirth(updatedUser.getDateOfBirth());
        if (updatedUser.getImage().length != 0)
            user.setImage(updatedUser.getImage());

        return userRepository.save(user);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Record> currentBooks(User user) {
        return recordRepository.findByUser(user)
                .stream().filter(a -> a.getReturnDate() == null).collect(Collectors.toList());
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Record> pastBooks(User user) {
        return recordRepository.findByUser(user)
                .stream().filter(a -> a.getReturnDate() != null).collect(Collectors.toList());
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void resetPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return;
        }

        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        user.setResetTokenExpireTime(LocalDateTime.now().plusMinutes(60));
        userRepository.save(user);

        String resetUrl = "http://localhost:8080/auth/reset/form/" + resetToken;
        String emailText = "Здравствуйте, " + user.getFullName() +
                "\nДля сброса пароля перейдите по ссылке: " + resetUrl;

        emailService.sendSimpleMessage(email, "Сброс пароля", emailText);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void saveNewPassword(String resetToken, String newPassword) {
        User user = userRepository.findByResetToken(resetToken);
        if (user == null || user.getResetTokenExpireTime().isBefore(LocalDateTime.now()))
            return;

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpireTime(null);
        userRepository.save(user);
    }

}