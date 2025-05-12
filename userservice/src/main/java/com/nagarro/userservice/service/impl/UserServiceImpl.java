package com.nagarro.userservice.service.impl;

import com.nagarro.userservice.entity.Users;
import com.nagarro.userservice.repository.UserRepository;
import com.nagarro.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String loadUserByUsername(String username){
        Users user = userRepository.findByUsername(username);
        System.out.println(user);
        if (user == null)
            return "Not found";
        return user.getUserRole();
    }

    public String create(String username, String password) {
        // Encodes the password and creates a new User object
        Users user = Users.builder()
                .username(username)
                .password(new BCryptPasswordEncoder().encode(password)) // Encrypts the password
                .userRole("customer") // Assigns default authority
                .build();

        // Saves the new user to the database
        userRepository.save(user);

        return "Create Successfully !";
    }
}
