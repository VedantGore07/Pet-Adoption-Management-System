package com.vedant.security_service.dao;

import java.util.Optional;
import java.util.UUID;

import com.vedant.security_service.entity.User;
import com.vedant.security_service.repository.UserRepository;
import org.springframework.stereotype.Component;


@Component
public class UserDAO {

    private final UserRepository userRepository;

    public UserDAO(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public Optional<User> findById(UUID id){
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

}