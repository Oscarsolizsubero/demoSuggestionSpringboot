package com.example.demo.mapper.suggestion;

import com.example.demo.config.JwtTokenUtil;
import com.example.demo.model.DTO.suggestion.SuggestionAddDTO;
import com.example.demo.model.DTO.suggestion.SuggestionDTO;
import com.example.demo.model.DTO.user.UserResponse;
import com.example.demo.model.Suggestion;
import com.example.demo.model.User;
import com.example.demo.model.Vote;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SuggestionMapper {

    public static Suggestion suggestionToModel(SuggestionAddDTO suggestionAddDTO,String username){
        User u = User.builder()
                .username(username)
                .build();
        return Suggestion.builder()
                .title(suggestionAddDTO.getTitle())
                .description(suggestionAddDTO.getDescription())
                .user(u)
                .build();

    }
    public static SuggestionDTO suggestionToResponse(Suggestion suggestion, List<Vote> uservoted){
        return SuggestionDTO.builder()
                .id(suggestion.getId())
                .title(suggestion.getTitle())
                .createdDate(suggestion.getCreatedDate())
                .quantityVote(suggestion.getQuantityVote())
                .description(suggestion.getDescription())
                .updatedDate(suggestion.getUpdatedDate())
                .status(suggestion.getStatus())
                .user(UserResponse.builder()
                        .id(suggestion.getUser().getId())
                        .name(suggestion.getUser().getUsername())
                        .build())
                .isVoted(uservoted.stream().anyMatch(uv-> uv.getSuggestion().getId() == suggestion.getId() && uv.getUser().getId()==suggestion.getUser().getId()))
                .build();
    }
    public static List<SuggestionDTO> suggestionsToResponse(List<Suggestion> suggestions, List<Vote> uservoted){
        List<SuggestionDTO> lists = new ArrayList<>();
        suggestions.forEach(s-> lists.add(suggestionToResponse(s,uservoted)));
        return lists;
    }
}
