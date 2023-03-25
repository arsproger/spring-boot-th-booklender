package com.arsen.services;

import com.arsen.models.Book;
import com.arsen.models.User;
import com.arsen.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void saveUser(User User) {
        userRepository.save(User);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public User updateUser(Long id, User user){
        User updatedUser = getUserById(id);
        updatedUser.setEmail(user.getEmail());
        updatedUser.setPassword(user.getPassword());
        updatedUser.setFullName(user.getFullName());
        updatedUser.setDateOfBirth(user.getDateOfBirth());
        return userRepository.save(updatedUser);
    }
    public void updateCurrentBooksList(Long id, List<Book> currentBooks){
        User user = getUserById(id);
        user.setCurrentBooks(currentBooks);

    }
}