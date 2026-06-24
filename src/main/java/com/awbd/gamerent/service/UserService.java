package com.awbd.gamerent.service;

import com.awbd.gamerent.exception.ResourceNotFoundException;
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
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilizatorul cu ID-ul " + id + " nu a fost găsit!"));
    }


    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}