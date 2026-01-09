package com.vedant.security_service.service.impl;

import java.util.Optional;
import java.util.UUID;

import com.vedant.security_service.dao.UserDAO;
import com.vedant.security_service.entity.User;
import com.vedant.security_service.exception.UserAlreadyExistsException;
import com.vedant.security_service.service.UserService;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User registerUser(User user) {

        if (userDAO.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        if (userDAO.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        user.setActive(true);

        return userDAO.save(user);
    }

    @Override
    public Optional<User> getUserById(UUID id) {
        return userDAO.findById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    public Optional<User> authenticate(String username, String password){

        Optional<User> userOptional = userDAO.findByUsername(username);

        if (userOptional.isPresent()){
            User user = userOptional.get();
            if(user.getPassword().equals(password) && user.isActive()){
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }
}