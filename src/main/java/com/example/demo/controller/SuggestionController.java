package com.example.demo.controller;

import com.example.demo.config.JwtTokenUtil;
import com.example.demo.mapper.suggestion.SuggestionMapper;
import com.example.demo.model.DTO.suggestion.SuggestionAddDTO;
import com.example.demo.model.DTO.suggestion.SuggestionDTO;
import com.example.demo.model.DTO.suggestion.SuggestionEditStatusDTO;
import com.example.demo.model.Suggestion;
import com.example.demo.model.Vote;
import com.example.demo.service.SuggestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/suggestion")
public class SuggestionController {

    final SuggestionService suggestionService;
    final JwtTokenUtil jwtTokenUtil;

    public SuggestionController(SuggestionService suggestionService, JwtTokenUtil jwtTokenUtil) {
        this.suggestionService = suggestionService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping("/suggestions")
    public ResponseEntity<List<SuggestionDTO>> getAllSuggestions(@RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null) {
            token = "";
        }
        String user = jwtTokenUtil.getUsernameFromTokenWithBearer(token);
        final List<Suggestion> suggestions = suggestionService.findAll(user);


        final List<Vote> uservoted = suggestionService.findVotes(user);
        final List<SuggestionDTO> suggestionsResponse = SuggestionMapper.suggestionsToResponse(suggestions, uservoted);
        return new ResponseEntity<>(suggestionsResponse, HttpStatus.OK);

    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<SuggestionDTO> createSuggestion(@RequestBody SuggestionAddDTO suggestionAdd, @RequestHeader("Authorization") String token) {
        String user = jwtTokenUtil.getUsernameFromTokenWithBearer(token);
        final Suggestion suggestionToSave = suggestionService.save(SuggestionMapper.suggestionToModel(suggestionAdd, user));
        final List<Vote> uservoted = suggestionService.findVotes(user);
        final SuggestionDTO suggestionResponse = SuggestionMapper.suggestionToResponse(suggestionToSave, uservoted);
        return new ResponseEntity<>(suggestionResponse, HttpStatus.OK);
    }

    @PostMapping("/vote/{suggestionId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<SuggestionDTO> voteSuggestion(@PathVariable long suggestionId, @RequestHeader("Authorization") String token) {
        String user = jwtTokenUtil.getUsernameFromTokenWithBearer(token);
        final Suggestion suggestionRetrieved = suggestionService.vote(suggestionId, user);
        final List<Vote> uservoted = suggestionService.findVotes(user);
        final SuggestionDTO suggestionResponse = SuggestionMapper.suggestionToResponse(suggestionRetrieved, uservoted);
        return new ResponseEntity<>(suggestionResponse, HttpStatus.OK);
    }

    @PutMapping("/edit/status")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MODERATOR')")
    public ResponseEntity<SuggestionDTO> editStatusSuggestion(@RequestBody SuggestionEditStatusDTO newStatus, @RequestHeader("Authorization") String token) {
        String user = jwtTokenUtil.getUsernameFromTokenWithBearer(token);
        final Suggestion suggestionRetrieved = suggestionService.editSuggestionStatus(newStatus.getSuggestionId(), newStatus.getStatusDescription());
        final List<Vote> uservoted = suggestionService.findVotes(user);
        final SuggestionDTO suggestionResponse = SuggestionMapper.suggestionToResponse(suggestionRetrieved, uservoted);
        return new ResponseEntity<>(suggestionResponse, HttpStatus.OK);
    }
}
