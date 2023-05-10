package com.arsen.services;

import com.arsen.enums.Provider;
import com.arsen.enums.Role;
import com.arsen.models.Record;
import com.arsen.models.User;
import com.arsen.repositories.RecordRepository;
import com.arsen.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        return userRepository.findAll(PageRequest.of(offset, 6, Sort.by("id").ascending()));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);
        user.setProvider(Provider.LOCAL);
        if (user.getImage() == null || user.getImage().length == 0) {
            try {
                user.setImage(defaultImage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        userRepository.save(user);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public byte[] defaultImage() throws IOException {
        String path = new PathMatchingResourcePatternResolver().getResource("classpath:").getFile().getParentFile().getParentFile().getAbsolutePath();
        return Files.readAllBytes(Paths.get(path + "\\src\\main\\resources\\static\\image\\default.jpg"));
    }

    public void updateUser(Long id, User updatedUser) {
        User user = getUserById(id);
        user.setFullName(updatedUser.getFullName());
        user.setDateOfBirth(updatedUser.getDateOfBirth());
        if (updatedUser.getImage().length != 0)
            user.setImage(updatedUser.getImage());

        userRepository.save(user);
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
    public boolean resetPassword(String email) {
        if (userRepository.findByEmail(email).isEmpty())
            return false;

        User user = new User();

        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        user.setResetTokenExpireTime(LocalDateTime.now().plusMinutes(60));
        userRepository.save(user);

        String resetUrl = "http://localhost:8080/auth/reset/form/" + resetToken;
        String emailText = "Здравствуйте, " + user.getFullName() +
                "\nДля сброса пароля перейдите по ссылке: " + resetUrl;

        emailService.sendSimpleMessage(email, "Сброс пароля", emailText);
        return true;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean saveNewPassword(String resetToken, String newPassword) {
        User user = userRepository.findByResetToken(resetToken);
        if (user == null || user.getResetTokenExpireTime().isBefore(LocalDateTime.now()))
            return false;

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpireTime(null);
        userRepository.save(user);
        return true;
    }

    public boolean isPresentEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void processOAuthPostLogin(String username, String name, String registrationId) {
        if (userRepository.findByEmail(username).isEmpty()) {
            User user = new User();
            user.setRole(Role.ROLE_USER);
            user.setProvider(registrationId.equals("google")
                    ? Provider.GOOGLE
                    : Provider.GITHUB);
            user.setFullName(name);
            user.setEmail(username);

            if (user.getImage() == null || user.getImage().length == 0) {
                try {
                    user.setImage(defaultImage());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            userRepository.save(user);
        }

    }

}