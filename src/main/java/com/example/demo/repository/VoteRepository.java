package com.example.demo.repository;

import com.example.demo.model.Suggestion;
import com.example.demo.model.User;
import com.example.demo.model.Vote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends CrudRepository<Vote, Long> {
    Vote findByUserAndSuggestion(User user, Suggestion suggestion);
    List<Vote> findAllByUser(User user);
}
