package com.example.demo.controller;

import com.example.demo.config.JwtTokenUtil;
import com.example.demo.mapper.suggestion.SuggestionMapper;
import com.example.demo.model.DTO.suggestion.SuggestionAddDTO;
import com.example.demo.model.DTO.suggestion.SuggestionDTO;
import com.example.demo.model.Suggestion;
import com.example.demo.model.User;
import com.example.demo.service.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/suggestion")
public class SuggestionController {

    @Autowired
    SuggestionService suggestionService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @GetMapping("/suggestionsprivate")
    public List<SuggestionDTO> getAllsuggestions(@RequestHeader("Authorization") String token) {
        String user = jwtTokenUtil.getUsernameFromToken(token);
        final List<SuggestionDTO> suggestions = suggestionService.findAllPrivate(user);

        return (List<SuggestionDTO>) new ResponseEntity<>(suggestions, HttpStatus.OK);

    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity < Suggestion >createSuggestion(@RequestBody SuggestionAddDTO suggestionAdd, @RequestHeader("Authorization") String token){
        String user = jwtTokenUtil.getUsernameFromTokenWithBearer(token);
        final Suggestion suggestionToSave = suggestionService.save(SuggestionMapper.suggestionToModel(suggestionAdd, user));

        return new ResponseEntity<>(suggestionToSave, HttpStatus.OK);
    }
}
