package com.example.demo.service;

import com.example.demo.config.JwtTokenUtil;
import com.example.demo.model.DTO.suggestion.SuggestionDTO;
import com.example.demo.model.Status;
import com.example.demo.model.Suggestion;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.StatusRepository;
import com.example.demo.repository.SuggestionRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SuggestionService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SuggestionRepository suggestionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private StatusRepository statusRepository;

    public List<SuggestionDTO> findAllPrivate(String token) {

        return null;
    }
    public Suggestion save(Suggestion suggestion) {
        User user = userRepository.findByUsername(suggestion.getUser().getUsername());
        suggestion.setUser(user);
        Set<User> uservotes = new HashSet<>();
        //Set<Suggestion> usersuggestions = new HashSet<>();
        uservotes.add(user);
        //suggestion.setVotesUserSuggestions(uservotes);
        suggestion.setQuantityVote(1);
        suggestion.setCreatedDate(new Date());
        Status status = new Status();
        if(user.getRoles().contains(roleRepository.findByName("ADMIN")) || user.getRoles().contains(roleRepository.findByName("MODERATOR")))
        {
            status = statusRepository.findByDescription("Suggested");
        }
        else
        {
            status = statusRepository.findByDescription("New");
        }
        suggestion.setStatus(status);
        //usersuggestions.add(suggestion);
        //suggestion.author.setSuggestions(usersuggestions);

        Suggestion s = suggestionRepository.save(suggestion);
        suggestionRepository.insertVote(s.getUser().getId(),s.getId());
        return s;

    }
}
