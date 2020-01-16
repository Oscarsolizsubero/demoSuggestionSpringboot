package com.example.demo.service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;


import com.example.demo.mapper.user.UserDetailsMapper;
import com.example.demo.model.Role;
import com.example.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return UserDetailsMapper.build(user);
    }

    public User save(User user) {
        Role userRole = roleRepository.findByName("USER");
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);


        User newUser = User.builder().username(user.getUsername())
                .password(bcryptEncoder.encode(user.getPassword()))
                .roles(roles).build();
        return userRepository.save(newUser);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}