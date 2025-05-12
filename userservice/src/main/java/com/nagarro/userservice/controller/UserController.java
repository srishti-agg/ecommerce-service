package com.nagarro.userservice.controller;

import com.nagarro.userservice.dto.request.UserRequest;
import com.nagarro.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    private ResponseEntity<Object> createUser(@RequestBody UserRequest userRequest){

        if(userRequest == null){
            return ResponseEntity.badRequest().body("Please provide valid user details!");
        }
        String res = userService.create(userRequest.username(),userRequest.password());
//        return ResponseEntity.created().body(res);
        return ResponseEntity.created(URI.create("")).body(res);
    }

    @GetMapping
    private boolean isUserAdmin(@RequestParam String username){

        String role = userService.loadUserByUsername(username);
        return ("ADMIN").equalsIgnoreCase(role);
    }
}
