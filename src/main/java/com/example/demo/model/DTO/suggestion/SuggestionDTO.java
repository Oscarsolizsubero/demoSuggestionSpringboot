package com.example.demo.model.DTO.suggestion;

import com.example.demo.model.DTO.user.UserResponse;
import com.example.demo.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class SuggestionDTO {

    private long id;
    @Length(min = 3, max = 125)
    private String title;

    @Length(min = 3, max = 8000)
    private String description;
    private int quantityVote;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    private UserResponse user;

    private boolean isVoted;

    private Status status;
}
