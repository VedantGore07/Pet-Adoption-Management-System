package com.vedant.security_service.service;


import com.vedant.security_service.entity.User;


import java.util.Optional;
import java.util.UUID;

public interface UserService {

    User registerUser(User user);

    Optional<User> getUserById(UUID id);

    Optional<User> getUserByUsername(String username);

    Optional<User> getUserByEmail(String email);

    Optional<User> authenticate(String username, String password);

}