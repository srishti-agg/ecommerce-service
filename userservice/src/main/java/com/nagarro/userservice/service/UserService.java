package com.nagarro.userservice.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;


public interface UserService {

    String loadUserByUsername(String username)
            throws UsernameNotFoundException;

    String create(String username, String password);

}
