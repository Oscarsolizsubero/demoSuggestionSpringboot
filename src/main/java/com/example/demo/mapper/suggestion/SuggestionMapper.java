package com.example.demo.mapper.suggestion;

import com.example.demo.config.JwtTokenUtil;
import com.example.demo.model.DTO.suggestion.SuggestionAddDTO;
import com.example.demo.model.Suggestion;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;

public class SuggestionMapper {


    public static Suggestion suggestionToModel(SuggestionAddDTO suggestionAddDTO,String username){
        User u = User.builder().username(username).build();
        return Suggestion.builder().title(suggestionAddDTO.getTitle()).description(suggestionAddDTO.getDescription()).user(u).build();

    }
}
