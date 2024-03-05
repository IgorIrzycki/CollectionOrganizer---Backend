package com.example.collectionorganizer.service;

import com.example.collectionorganizer.model.User;
import com.example.collectionorganizer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUserByUsername(String name) {
        return userRepository.findByUsername(name);
    }
}
