package com.example.demo.service;

import com.example.demo.config.JwtTokenUtil;
import com.example.demo.model.DTO.suggestion.SuggestionDTO;
import com.example.demo.model.DTO.suggestion.SuggestionEditStatusDTO;
import com.example.demo.model.DTO.vote.VoteDTO;
import com.example.demo.model.Status;
import com.example.demo.model.Suggestion;
import com.example.demo.model.User;
import com.example.demo.model.Vote;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SuggestionService {

    private final UserRepository userRepository;
    private final SuggestionRepository suggestionRepository;
    private final RoleRepository roleRepository;
    private final StatusRepository statusRepository;
    private final VoteRepository voteRepository;

    public SuggestionService(UserRepository userRepository, SuggestionRepository suggestionRepository, RoleRepository roleRepository, StatusRepository statusRepository, VoteRepository voteRepository) {
        this.userRepository = userRepository;
        this.suggestionRepository = suggestionRepository;
        this.roleRepository = roleRepository;
        this.statusRepository = statusRepository;
        this.voteRepository = voteRepository;
    }

    public List<Suggestion> findAll(String username) {
        List<Status> statusesSearch = statusRepository.findByDescriptionIn(Arrays.asList("Suggested", "Fulfilled"));
        User user = userRepository.findByUsername(username);
        if (user != null) {
            if (userIsAdmin(user)) {
                return suggestionRepository.findAllByOrderByQuantityVoteDesc();
            }
            return suggestionRepository.findByStatusIdByOrderByQuantityVote(statusesSearch);
        }
        return suggestionRepository.findByStatusIdByOrderByQuantityVote(statusesSearch);
    }

    public Suggestion save(Suggestion suggestion) {
        User user = userRepository.findByUsername(suggestion.getUser().getUsername());
        suggestion.setUser(user);
        suggestion.setCreatedDate(new Date());
        Status status;
        if (userIsAdmin(user)) {
            status = statusRepository.findByDescription("Suggested");
        } else {
            status = statusRepository.findByDescription("New");
        }
        suggestion.setStatus(status);
        return saveVote(suggestion,user);
    }

    public List<Vote> findVotes(String username) {
        User user = userRepository.findByUsername(username);
        return voteRepository.findAllByUser(user);
    }


    public Suggestion vote(long suggestionId, String username) {
        User user = userRepository.findByUsername(username);
        Suggestion suggestion = suggestionRepository.findById(suggestionId);
        if (!userIsAdmin(user)) {
            if (!normalUserCanVote(suggestion.getStatus().getDescription())) {
                return null;
            }
        }
        if (userHasVoted(suggestion,user)){
            return deleteVote(suggestion, user);
        }
        return saveVote(suggestion, user);
    }

    public Suggestion editSuggestionStatus(long suggestionId,String newStatus) {
        Suggestion suggestion = suggestionRepository.findById(suggestionId);
        Status status = statusRepository.findByDescription(newStatus);
        if(status==null){
            return null;
        }
        suggestion.setStatus(status);
        return suggestionRepository.save(suggestion);
    }

    private boolean userHasVoted(Suggestion suggestion, User user) {
        if (voteRepository.findByUserAndSuggestion(user,suggestion)!=null) {
            return true;
        }
        return false;
    }

    private Suggestion saveVote(Suggestion suggestion, User user) {
        suggestion.setQuantityVote(suggestion.getQuantityVote() + 1);
        Suggestion s = suggestionRepository.save(suggestion);
        Vote vote = Vote.builder().user(user).suggestion(s).build();
        voteRepository.save(vote);
        return s;
    }

    private Suggestion deleteVote(Suggestion suggestion, User user) {
        suggestion.setQuantityVote(suggestion.getQuantityVote() - 1);
        Suggestion s = suggestionRepository.save(suggestion);
        voteRepository.delete(voteRepository.findByUserAndSuggestion(user,suggestion));
        return s;
    }

    private boolean userIsAdmin(User user) {
        if (user.getRoles().contains(roleRepository.findByName("ADMIN")) ||
                user.getRoles().contains(roleRepository.findByName("MODERATOR"))) {
            return true;
        }
        return false;
    }

    private boolean normalUserCanVote(String suggestion) {
        return suggestion.equals("Suggested") || suggestion.equals("Fulfilled");
    }
}
