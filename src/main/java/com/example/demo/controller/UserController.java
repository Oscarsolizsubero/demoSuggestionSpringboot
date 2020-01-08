package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.JwtUserDetailsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final JwtUserDetailsService userDetailsService;

    public UserController(JwtUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userDetailsService.findAll();
    }

}
