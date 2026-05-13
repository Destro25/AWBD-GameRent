package com.awbd.gamerent.service;

import com.awbd.gamerent.model.User;
import com.awbd.gamerent.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User saveUser(User user) {
        return userRepository.save(user);
    }


    public List<User> findAllUsers() {
        return userRepository.findAll();
    }


    public User findUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new RuntimeException("Utilizatorul cu ID-ul " + id + " nu a fost gasit!");
        }
    }


    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}