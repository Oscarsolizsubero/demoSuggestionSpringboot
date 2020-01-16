package com.example.demo.service;

import com.example.demo.model.Status;
import com.example.demo.model.Suggestion;
import com.example.demo.model.User;
import com.example.demo.model.Vote;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SuggestionService {

    private final UserRepository userRepository;
    private final SuggestionRepository suggestionRepository;
    private final StatusRepository statusRepository;
    private final VoteRepository voteRepository;

    private static final String STATUS_NEW = "New";

    private static final String STATUS_SUGGESTED = "Suggested";

    private static final String STATUS_FULFILLED = "Fulfilled";

    public SuggestionService(UserRepository userRepository, SuggestionRepository suggestionRepository, StatusRepository statusRepository, VoteRepository voteRepository) {
        this.userRepository = userRepository;
        this.suggestionRepository = suggestionRepository;
        this.statusRepository = statusRepository;
        this.voteRepository = voteRepository;
    }

    public List<Suggestion> findAll(String username) {
        List<Status> publicStatuses = statusRepository.findByNameIn(Arrays.asList(STATUS_SUGGESTED, STATUS_FULFILLED));
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return suggestionRepository.findByStatusIdByOrderByQuantityVote(publicStatuses);
        }
        if (user.isAdmin()) {
            return suggestionRepository.findAllByOrderByQuantityVoteDesc();
        }
        return suggestionRepository.findByStatusIdByOrderByQuantityVote(publicStatuses);
    }

    public Suggestion save(Suggestion suggestion) {
        User user = userRepository.findByUsername(suggestion.getUser().getUsername());
        suggestion.setUser(user);
        suggestion.setCreatedDate(new Date());
        Status status;
        if (user.isAdmin()) {
            status = statusRepository.findByName(STATUS_SUGGESTED);
        } else {
            status = statusRepository.findByName(STATUS_NEW);
        }
        suggestion.setStatus(status);
        return saveAndVote(suggestion, user);
    }

    public List<Vote> findVotes(String username) {
        User user = userRepository.findByUsername(username);
        return voteRepository.findAllByUser(user);
    }

    public Suggestion vote(long suggestionId, String username) {
        User user = userRepository.findByUsername(username);
        Suggestion suggestion = suggestionRepository.findById(suggestionId);
        if (!user.isAdmin() && !suggestion.canBeVoteByNormalUser()) {
            return null;
        }
        //check if user has voted
        if (voteRepository.findByUserAndSuggestion(user, suggestion) != null) {
            return deleteAndVote(suggestion, user);
        }
        return saveAndVote(suggestion, user);
    }

    public Suggestion editSuggestionStatus(long suggestionId, String newStatus) {
        Suggestion suggestion = suggestionRepository.findById(suggestionId);
        Status status = statusRepository.findByName(newStatus);
        if (status == null) {
            return null;
        }
        suggestion.setStatus(status);
        return suggestionRepository.save(suggestion);
    }

    private Suggestion saveAndVote(Suggestion suggestion, User user) {
        suggestion.setQuantityVote(suggestion.getQuantityVote() + 1);
        Suggestion s = suggestionRepository.save(suggestion);
        Vote vote = Vote.builder().user(user).suggestion(s).build();
        voteRepository.save(vote);
        return s;
    }

    private Suggestion deleteAndVote(Suggestion suggestion, User user) {
        suggestion.setQuantityVote(suggestion.getQuantityVote() - 1);
        Suggestion s = suggestionRepository.save(suggestion);
        voteRepository.delete(voteRepository.findByUserAndSuggestion(user, suggestion));
        return s;
    }
}
