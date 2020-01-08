package com.example.demo.model.DTO.suggestion;

import com.example.demo.model.DTO.user.UserResponse;
import com.example.demo.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;


public class SuggestionDTO extends SuggestionAddDTO{

    private int id;

    private int quantityVote;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    private UserResponse user;

    private boolean isVoted;

    private Status status;
}
